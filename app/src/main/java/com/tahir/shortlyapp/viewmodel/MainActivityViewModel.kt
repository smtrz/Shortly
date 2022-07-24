package com.tahir.shortlyapp.viewmodel

import androidx.lifecycle.*
import com.tahir.shortlyapp.db.Links
import com.tahir.shortlyapp.generics.NetworkResult
import com.tahir.shortlyapp.models.Shorten
import com.tahir.shortlyapp.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * ViewModel that contains operation and connects to the repository for all the domain logic
 * @constructor injected Repository
 */
@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    // Livedata getters and private variables
    // for network requests that returns the shortlink for provided url.
    private val _response: MutableLiveData<NetworkResult<Shorten>> = MutableLiveData()
    val response: MutableLiveData<NetworkResult<Shorten>>
        get() = _response

    // for database insertion operation.
    private var _linkInsertresponse: MutableLiveData<Long> = MutableLiveData()
    val linkInsertresponse: MutableLiveData<Long>
        get() = _linkInsertresponse

    // for database deletion operation.
    private var _deleteLinkResponse: MutableLiveData<Int> = MutableLiveData()
    val deleteLinkResponse: MutableLiveData<Int>
        get() = _deleteLinkResponse

    /**
     * It launches the coroutines for shortUrl and collects all the values emitted by flow, and post
     * it via LiveData
     * @param url
     */
    fun getShortUrl(url: String) {

        viewModelScope.launch {
            repository.shortenUrl(url).onEach { values -> _response.postValue(values) }.collect()
        }
    }

    /**
     * It launches the coroutine on IO dispatcher to insert the link into the db and post the
     * response.
     * @param link
     */
    fun insertLink(link: Links) {

        viewModelScope.launch(Dispatchers.IO) {
            _linkInsertresponse.postValue(repository.insertLink(link))
        }
    }

    /**
     * It launches the coroutine on IO dispatcher of LiveDataScope to get all the links from the db
     * and emitSource
     */
    val getAllLinks: LiveData<List<Links>> =
        liveData(Dispatchers.IO) { emitSource(repository.getAllLinks()) }

    /**
     * It launches the coroutine on IO dispatcher on viewmodel scope to delete the link into the db
     * and post the response.
     * @param id
     */
    fun deleteLink(id: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            _deleteLinkResponse.postValue(repository.deleteLink(id))
        }
    }
}
