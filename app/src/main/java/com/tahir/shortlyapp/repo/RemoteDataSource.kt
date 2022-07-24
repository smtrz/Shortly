package com.tahir.shortlyapp.repo

import com.tahir.shortlyapp.Interfaces.ShortenService
import javax.inject.Inject

/**
 * all operations related to remote datasource are contained in RemoteDataSource
 * @constructor shortenService
 */
class RemoteDataSource @Inject constructor(private val shortenService: ShortenService) {
    // suspended function that calls the getShortUrl method
    suspend fun shortenUrl(url: String) =
        shortenService.getShortUrl(url)

}