package com.tahir.shortlyapp.repo


import com.tahir.shortlyapp.generics.NetworkResult
import com.tahir.shortlyapp.models.Shorten
import com.zenjob.android.browsr.generics.BaseApiResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : BaseApiResponse() {

    suspend fun shortenUrl(url: String): Flow<NetworkResult<Shorten>> {
        return flow {
            emit(NetworkResult.Loading())
            emit(safeApiCall { remoteDataSource.shortenUrl(url) })
        }.flowOn(Dispatchers.IO)
    }


}