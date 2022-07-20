package com.tahir.shortlyapp.repo

import com.tahir.shortlyapp.Interfaces.ShortenService
import javax.inject.Inject

open class RemoteDataSource @Inject constructor(private val shortenService: ShortenService) {

    suspend fun shortenUrl(url: String) =
        shortenService.getShortUrl(url)

}