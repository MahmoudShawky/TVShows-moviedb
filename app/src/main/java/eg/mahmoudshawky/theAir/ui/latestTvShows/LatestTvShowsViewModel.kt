package eg.mahmoudshawky.theAir.ui.latestTvShows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import eg.mahmoudshawky.theAir.data.Repository
import eg.mahmoudshawky.theAir.data.models.entity.TvShow
import eg.mahmoudshawky.theAir.data.models.network.LatestTvShowsResponse
import eg.mahmoudshawky.theAir.data.remote.Result
import eg.mahmoudshawky.theAir.ui.base.BaseViewModel
import eg.mahmoudshawky.theAir.utils.FetchType
import eg.mahmoudshawky.theAir.utils.NetworkState
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class LatestTvShowsViewModel(
    private val repository: Repository,
    var tvShowId: Int?,
) : BaseViewModel(repository) {

    private val _tvShowsLiveData = MutableLiveData<List<TvShow>>()
    val tvShowsLiveData: LiveData<List<TvShow>> get() = _tvShowsLiveData

    private var fetchType: FetchType? by Delegates.observable(null) { _, _, _ ->
        when (fetchType) {
            FetchType.LATEST -> fetchLatestTvShows()
            FetchType.RECOMMENDED -> tvShowId?.let { fetchRecommendedTvShows(it) }
            FetchType.SIMILAR -> tvShowId?.let { fetchSimilar(it) }
        }
    }

    fun updateFetchType(fetchType: FetchType?) {
        this.fetchType = fetchType
    }

/*    init {
        when(fetchType){
            null,
            FetchType.LATEST -> fetchLatestTvShows()
            FetchType.RECOMMENDED -> fetchRecommendedTvShows()
            FetchType.SIMILAR -> fetchSimilar()
        }
    }*/

    fun fetchLatestTvShows() {
        viewModelScope.launch(handler) {
            _networkState.postValue(NetworkState.LOADING)
            showResult(repository.fetchLatestTvShows(1))
        }
    }

    fun fetchRecommendedTvShows(id: Int) {
        viewModelScope.launch(handler) {
            _networkState.postValue(NetworkState.LOADING)
            showResult(repository.fetchRecommendationTv(id))
        }
    }

    fun fetchSimilar(id: Int) {
        viewModelScope.launch(handler) {
            _networkState.postValue(NetworkState.LOADING)
            showResult(repository.fetchSimilarTv(id))
        }
    }

    private fun showResult(result: Result<LatestTvShowsResponse>) {
        when (result) {
            is Result.Success -> {
                if (result.data != null) {
                    _networkState.postValue(NetworkState.LOADED)
                    _tvShowsLiveData.postValue(result.data.results)
                } else {
                    _networkState.postValue(NetworkState.NO_DATA)
                }
            }
            is Result.Error -> {
                _networkState.postValue(NetworkState.error(result.exception.msg))
            }
        }
    }
}