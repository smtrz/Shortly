package com.tahir.shortlyapp.repo

import androidx.lifecycle.LiveData
import com.tahir.shortlyapp.db.Links
import com.tahir.shortlyapp.generics.BaseApiResponse
import com.tahir.shortlyapp.generics.NetworkResult
import com.tahir.shortlyapp.models.Shorten
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Repository is the Single Source of truth that contains instance of both local and remote data
 * source.
 * @constructor remoteDataSource , localDataSource (using construction injection)
 */
@ActivityRetainedScoped
class Repository
@Inject
constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : BaseApiResponse() {
    /**
     * calls API for url shortening, emits data on IO Dispatcher.
     * @param url
     * @return Flow<NetworkResult<Shorten>>
     */
    suspend fun shortenUrl(url: String): Flow<NetworkResult<Shorten>> {
        return flow {
            emit(NetworkResult.Loading())
            emit(safeApiCall { remoteDataSource.shortenUrl(url) })
        }
            .flowOn(Dispatchers.IO)
    }

    /**
     * gets all links saved in database table from localdatasource.
     * @return LiveData<List<Links>>
     */
    fun getAllLinks(): LiveData<List<Links>> {
        return localDataSource.getallLinks()
    }

    /**
     * delete the link record from database table from id
     * @param id
     * @return int
     */
    suspend fun deleteLink(id: Int): Int {
        return localDataSource.deleteLink(id)
    }

    /**
     * insert the link object into the database table.
     * @param links
     * @return Long
     */
    suspend fun insertLink(links: Links): Long {
        return localDataSource.insertLink(links)
    }
}
