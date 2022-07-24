package com.tahir.shortlyapp.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tahir.shortlyapp.R
import com.tahir.shortlyapp.adapters.LinksAdapter
import com.tahir.shortlyapp.adapters.OnItemClickListener
import com.tahir.shortlyapp.db.Links
import com.tahir.shortlyapp.generics.NetworkResult
import com.tahir.shortlyapp.helpers.UiHelper
import com.tahir.shortlyapp.helpers.ValidationHelper
import com.tahir.shortlyapp.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnItemClickListener {
    // injecting LinkAdapter instance
    @Inject lateinit var linkAdapter: LinksAdapter
    var linkList: ArrayList<Links> = ArrayList()
    lateinit var mLayoutManager: LinearLayoutManager
    lateinit var link_data: Links

    // get view model instance via Hilt
    private val mainActivityVM by viewModels<MainActivityViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // calling init method that initializes the recyclerview and setup clicklistener
        init()
        getDataFromDB()
        subscribeObservers()
    }

    /** initializes the recyclerview and setup clicklistener */
    private fun init() {
        linkAdapter.setupClickListener(this)
        linkAdapter.setList(linkList)
        mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        shortlinklayout_list.layoutManager = mLayoutManager
        shortlinklayout_list.adapter = linkAdapter
    }

    /**
     * get data from database via livedata and subscribe to the data changes. based on the data
     * changes from the db, shows or hides the emptyView layout.
     */
    private fun getDataFromDB() {
        mainActivityVM.getAllLinks.observe(this) {
            if (it.isNotEmpty()) {
                emptyview.visibility = View.GONE
                shortlinklayout.visibility = View.VISIBLE
                linkAdapter.setList(it as MutableList<Links>)
            } else {
                emptyview.visibility = View.VISIBLE
                shortlinklayout.visibility = View.GONE
            }
        }
    }

    /**
     * invokes the getShortUrl method on viewmodel scope to fetch the shortUrl from API.
     * @param url
     */
    private fun getShortUrl(url: String) {
        mainActivityVM.getShortUrl(url)
    }

    /**
     * Submit method is bind to the button that gets the shortUrl from the API
     * @see getShortUrl It validates the input from the editText based on empty / validurl based on
     * the data changes from the db, shows or hides the emptyView layout.
     */
    fun onSubmitClick(v: View) {
        if (link.text.toString().equals("")) {

            UiHelper.showToast(this, "field cannot be empty")

            return
        }
        if (!ValidationHelper.isValidUrl(link.text.toString())) {

            UiHelper.showToast(this, "Url is not valid")

            return
        }
        getShortUrl(link.text.toString())
    }

    /**
     * listen to the response of the server for short link details, also listen to the responses for
     * link insertion and deletion from database
     */
    private fun subscribeObservers() {
        mainActivityVM.response.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    // got the response , hides the progress bar and invokes the insertLink method
                    UiHelper.displayProgressBar(false, progressbar, this)
                    response.data?.let {
                        if (it.ok == true) {
                            link_data =
                                Links(
                                    orignalLink = link.text.toString(),
                                    shortLink = it.result?.fullShortLink
                                )
                            mainActivityVM.insertLink(link_data)
                        }
                    }
                }
                is NetworkResult.Error -> {
                    // show error message
                    response.message?.let { UiHelper.showToast(this, it) }
                    UiHelper.displayProgressBar(false, progressbar, this)
                }
                is NetworkResult.Loading -> {
                    // show a progress bar
                    UiHelper.displayProgressBar(true, progressbar, this)
                }
            }
        }
        // observes, when the data is inserted it returns the row count, checks the row count and
        // intimate the user via toast message.
        mainActivityVM.linkInsertresponse.observe(this) {
            if (it > 0) {
                UiHelper.showToast(this, "link saved successfully.")
            }
        }
        // observes, when the data is deleted it returns the row count, checks the row count and
        // intimate the user via toast message.

        mainActivityVM.deleteLinkResponse.observe(this) {
            if (it > 0) {
                UiHelper.showToast(this, "record deleted successfully..")
            }
        }
    }

    /**
     * overriding method that check for the copy and delete button and call respective methods.
     * @param listitem
     * @param position
     * @param view
     */
    override fun onItemClick(listitem: Links, position: Int, view: View?) {
        when (view?.id) {
            R.id.delete -> {
                link_data = listitem
                mainActivityVM.deleteLink(link_data.id)
            }
            R.id.copy -> {
                // copy the text to clipboard and update the position to adapter.
                listitem.shortLink?.let { UiHelper.copyTextToClipboard(this, it) }
                linkAdapter.updatePositionForCopyButton(position)
            }
        }
    }
}
