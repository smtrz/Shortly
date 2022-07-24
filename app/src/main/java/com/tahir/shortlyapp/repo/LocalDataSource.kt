package com.tahir.shortlyapp.repo

import com.tahir.shortlyapp.db.Links
import com.tahir.shortlyapp.db.LinksDao
import javax.inject.Inject

/**a
 * holds all the local database operations
 * @constructor injection linkDao
 */
class LocalDataSource @Inject constructor(private val linksDao: LinksDao) {

    fun getallLinks() = linksDao.getAllLinks()

    suspend fun insertLink(links: Links) = linksDao.insertLink(links)

    suspend fun deleteLink(id: Int) = linksDao.deleteLink(id)
}
