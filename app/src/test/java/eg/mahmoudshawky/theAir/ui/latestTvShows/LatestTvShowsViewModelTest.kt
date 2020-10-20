package eg.mahmoudshawky.theAir.ui.latestTvShows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.timeout
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import eg.mahmoudshawky.theAir.data.Repository
import eg.mahmoudshawky.theAir.data.models.entity.TvShow
import eg.mahmoudshawky.theAir.data.remote.Failure
import eg.mahmoudshawky.theAir.data.remote.Result
import eg.mahmoudshawky.theAir.utils.FetchType
import eg.mahmoudshawky.theAir.utils.NetworkState
import eg.mahmoudshawky.theAir.utils.TestData
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Rule
import org.koin.test.KoinTest

class LatestTvShowsViewModelTest :KoinTest {
/*
    private val viewModel :LatestTvShowsViewModel by inject()
    private val repository : Repository by inject()

    @Mock
    lateinit var tvShowsLiveData : Observer<List<TvShow>>

    @Mock
    lateinit var networkState : Observer<NetworkState>

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext()
            modules(
                listOf(
                    repositoryModule,
                    viewModelModule,
                    retrofitModule,
                    apiModule,
                    databaseModule
                )
            )
        }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun getTvShowsLiveData() {
        runBlocking {
            whenever(repository.fetchLatestTvShows(1)).thenReturn(Result.Success(TestData.mockTvResponse))
            viewModel.networkState.observeForever(networkState)
            val result = repository.fetchLatestTvShows(1)
            delay(20)
            assertNotNull(viewModel.networkState.value)
            Mockito.verify(networkState, timeout(50)).onChanged(NetworkState.LOADING)
            Mockito.verify(networkState, timeout(50)).onChanged(NetworkState.LOADED)
        }
    }*/

    private lateinit var viewModel: LatestTvShowsViewModel
    //private lateinit var viewModel: LatestTvShowsViewModel
    private lateinit var repository: Repository
    private lateinit var tvShowsObserver: Observer<List<TvShow>>
    private lateinit var networkStateObserver: Observer<NetworkState>
    private val validTvId = 19965
    private val invalidTvId = -1

    //private val successResource = Result.Success(TestData.mockTvResponse)
    private val errorResult = Result.Error(Failure.UnknownException("Unknown"))
    private val errorState = NetworkState.error("Unknown")

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        repository = mock()
        runBlocking {
            whenever(repository.fetchLatestTvShows(1)).thenReturn(Result.Success(TestData.mockTvResponse))
            whenever(repository.fetchRecommendationTv(validTvId)).thenReturn(Result.Success(TestData.mockRecommendedTvResponse))
            whenever(repository.fetchRecommendationTv(invalidTvId)).thenReturn(errorResult)
            whenever(repository.fetchSimilarTv(validTvId)).thenReturn(Result.Success(TestData.mockSimilarTvResponse))
            whenever(repository.fetchSimilarTv(invalidTvId)).thenReturn(errorResult)
        }
        viewModel = LatestTvShowsViewModel(repository, validTvId)
        tvShowsObserver = mock()
        networkStateObserver = mock()
    }

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `when fetchType is LATEST , then fetchLatestTvShows called and observer is success with mockTvResponse`() = runBlocking {
        viewModel.tvShowsLiveData.observeForever(tvShowsObserver)
        viewModel.networkState.observeForever(networkStateObserver)

        viewModel.updateFetchType(FetchType.LATEST)
        delay(10)
        verify(repository).fetchLatestTvShows(1)
        verify(networkStateObserver, timeout(30)).onChanged(NetworkState.LOADING)
        verify(tvShowsObserver, timeout(50)).onChanged(TestData.mockTvResponse.results)
    }

    @Test
    fun `when fetchType is RECOMMENDED , then fetchRecommendationTv called and observer is success with mockRecommendedTvResponse `() = runBlocking {
        viewModel.tvShowsLiveData.observeForever(tvShowsObserver)
        viewModel.networkState.observeForever(networkStateObserver)

        viewModel.updateFetchType(FetchType.RECOMMENDED)
        delay(10)
        verify(repository).fetchRecommendationTv(validTvId)
        verify(networkStateObserver, timeout(30)).onChanged(NetworkState.LOADING)
        verify(tvShowsObserver, timeout(50)).onChanged(TestData.mockRecommendedTvResponse.results)
    }

    @Test
    fun `when fetchType is RECOMMENDED and invalid tvId , then fetchRecommendationTv called and observer is success with mockRecommendedTvResponse `() = runBlocking {
        viewModel.tvShowsLiveData.observeForever(tvShowsObserver)
        viewModel.networkState.observeForever(networkStateObserver)

        viewModel.tvShowId = invalidTvId
        viewModel.updateFetchType(FetchType.RECOMMENDED)
        delay(10)
        verify(repository).fetchRecommendationTv(invalidTvId)
        verify(networkStateObserver, timeout(30)).onChanged(NetworkState.LOADING)
        verify(networkStateObserver, timeout(50)).onChanged(errorState)
    }

    @Test
    fun `when fetchType is SIMILAR , then fetchSimilarTv called and networkStateObserver has unknown error`() = runBlocking {
        viewModel.tvShowsLiveData.observeForever(tvShowsObserver)
        viewModel.networkState.observeForever(networkStateObserver)

        viewModel.updateFetchType(FetchType.SIMILAR)
        delay(10)
        verify(repository).fetchSimilarTv(validTvId)
        verify(networkStateObserver, timeout(30)).onChanged(NetworkState.LOADING)
        verify(tvShowsObserver, timeout(50)).onChanged(TestData.mockSimilarTvResponse.results)
    }


    @Test
    fun `when fetchType is SIMILAR and invalid tvId , then fetchSimilarTv called and networkStateObserver has unknown error`() = runBlocking {
        viewModel.tvShowsLiveData.observeForever(tvShowsObserver)
        viewModel.networkState.observeForever(networkStateObserver)

        viewModel.tvShowId = invalidTvId
        viewModel.updateFetchType(FetchType.SIMILAR)
        delay(10)
        verify(repository).fetchSimilarTv(invalidTvId)
        verify(networkStateObserver, timeout(30)).onChanged(NetworkState.LOADING)
        verify(networkStateObserver, timeout(50)).onChanged(errorState)
    }


}