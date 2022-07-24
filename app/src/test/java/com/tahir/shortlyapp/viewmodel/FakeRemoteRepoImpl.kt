package com.tahir.shortlyapp.viewmodel

import com.tahir.shortlyapp.Interfaces.ShortenService
import com.tahir.shortlyapp.models.Result
import com.tahir.shortlyapp.models.Shorten
import retrofit2.Response

/**
 * fake Implementation for ShortenService
 */
class FakeRemoteRepoImpl() : ShortenService {


    override suspend fun getShortUrl(url: String?): Response<Shorten> {
        var fakeShortenObj = Shorten()
        fakeShortenObj.ok = true
        fakeShortenObj.result = Result(
            "stoR56",
            "shrtco.de\\/stoRS6",
            "https:\\/\\/shrtco.de\\/stoRS6",
            "9qr.de\\/stoRS6"
        )
        return Response.success(fakeShortenObj)

    }
}

