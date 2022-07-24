package com.tahir.shortlyapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LinksDao {
    // selects all the links data and order by id
    @Query("SELECT * FROM links order by id DESC")
    fun getAllLinks(): LiveData<List<Links>>

    // handles the data and handles the conflict by replacing
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLink(link: Links): Long

    // delete the link data from id
    @Query("DELETE FROM links WHERE id = :id")
    fun deleteLink(id: Int): Int
}
