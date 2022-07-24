package com.tahir.shortlyapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.tahir.shortlyapp.generics.NetworkResult
import com.tahir.shortlyapp.models.Shorten
import com.tahir.shortlyapp.repo.LocalDataSource
import com.tahir.shortlyapp.repo.RemoteDataSource
import com.tahir.shortlyapp.repo.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.concurrent.CountDownLatch

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainActivityViewModelTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    lateinit var remoteDataSource: RemoteDataSource
    lateinit var repository: Repository

    @Mock
    lateinit var localDataSource: LocalDataSource
    lateinit var viewModel: MainActivityViewModel

    @Mock
    lateinit var shorten_data_observer: Observer<NetworkResult<Shorten>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        var fakeRemoteRepoImpl = FakeRemoteRepoImpl()
        remoteDataSource = RemoteDataSource(fakeRemoteRepoImpl)
        repository = Repository(remoteDataSource, localDataSource)
        viewModel = MainActivityViewModel(repository)
    }

    /**
     * Unit testing the method that provide us the short url, have used fake remote repository
     * implementation
     */
    @Test
    fun getShortUrl() = runBlocking {
        var short_url: Shorten? = null

        viewModel.getShortUrl("google.com/tahir-raza")

        val countDownLatch = CountDownLatch(1)
        shorten_data_observer =
            Observer<NetworkResult<Shorten>> { datastate ->
                when (datastate) {
                    is NetworkResult.Success<Shorten> -> {
                        short_url = datastate?.data!!
                        countDownLatch.countDown()
                    }
                    is NetworkResult.Error<Shorten> -> {
                        Assert.assertTrue(false)
                    }
                }
            }
        viewModel.response.observeForever(shorten_data_observer)
        countDownLatch.await()
        Assert.assertEquals(short_url?.ok, true)
    }
}
