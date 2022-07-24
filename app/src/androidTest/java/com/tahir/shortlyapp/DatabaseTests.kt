package com.tahir.shortlyapp

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.tahir.shortlyapp.db.AppDatabase
import com.tahir.shortlyapp.db.Links
import com.tahir.shortlyapp.db.LinksDao
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTests {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var db: AppDatabase
    private lateinit var dao: LinksDao

    @Before
    fun setUp() {
        // setting up in memory database
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        // getting data access object from the database created.
        dao = db.linkDao()
    }

    @Test
    fun insertLink() = runBlocking {
        val link = Links(id = 1, shortLink = "somshort.ly", orignalLink = "google.com/tahir-raza")
        Assert.assertTrue(dao.insertLink(link) > 0)
    }

    @Test
    fun getLinks() = runBlocking {
        val link = Links(id = 1, shortLink = "somshort.ly", orignalLink = "google.com/tahir-raza")
        dao.insertLink(link)
        var listofLinks = dao.getAllLinks().getOrAwaitValue()
        Assert.assertTrue(listofLinks.size > 0)
    }

    @Test
    fun deleteLink() = runBlocking {
        val link = Links(id = 1, shortLink = "somshort.ly", orignalLink = "google.com/tahir-raza")
        dao.insertLink(link)
        Assert.assertTrue(dao.deleteLink(1) == 1)
    }

    @After
    fun tearDown() {
        // closing the database after it has been used.
        db.close()
        // removing the observer afterwards

    }
}
