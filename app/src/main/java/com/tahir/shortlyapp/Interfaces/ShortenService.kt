package com.tahir.shortlyapp.Interfaces


import com.tahir.shortlyapp.Constants.WebServiceConstants
import com.tahir.shortlyapp.models.Shorten
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Shorten Service contains suspend methods for the network calls
 */
interface ShortenService {

    @POST(WebServiceConstants.SHORTEN)
    suspend fun getShortUrl(
        @Query("url") url: String? = null
    ): Response<Shorten>


}