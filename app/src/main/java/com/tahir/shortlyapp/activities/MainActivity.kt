package com.tahir.shortlyapp.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tahir.shortlyapp.R
import com.tahir.shortlyapp.databinding.ActivityMainBinding
import com.tahir.shortlyapp.generics.NetworkResult
import com.tahir.shortlyapp.helpers.UiHelper
import com.tahir.shortlyapp.helpers.ValidationHelper
import com.tahir.shortlyapp.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    //get view model instance via Hilt
    private val mainActivityVM by viewModels<MainActivityViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // initialization for data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        subscribeObservers()

    }

    fun getShortUrl(url: String) {
        mainActivityVM.getShortUrl(url)

    }

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
     * listen to the response of the server for short link details.
     */
    fun subscribeObservers() {
        mainActivityVM.response.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    // got the response , sets the data binding and also hides the progress bar
                    UiHelper.displayProgressBar(false, progressbar, this)
                    response.data?.let {

                        Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
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

    }
}