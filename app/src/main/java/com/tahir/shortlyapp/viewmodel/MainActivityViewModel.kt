package com.tahir.shortlyapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tahir.shortlyapp.generics.NetworkResult
import com.tahir.shortlyapp.models.Shorten
import com.tahir.shortlyapp.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    // Livedata getters and private variables
    private val _response: MutableLiveData<NetworkResult<Shorten>> = MutableLiveData()

    val response: MutableLiveData<NetworkResult<Shorten>>
        get() = _response

    fun getShortUrl(url: String) {

        viewModelScope.launch {

            repository
                .shortenUrl(url)
                .onEach { values -> _response.postValue(values) }
                .collect()
        }

    }
}
