package eg.mahmoudshawky.theAir.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import eg.mahmoudshawky.theAir.data.Repository
import eg.mahmoudshawky.theAir.data.models.entity.TvShow
import eg.mahmoudshawky.theAir.data.models.network.*
import eg.mahmoudshawky.theAir.data.remote.Result
import eg.mahmoudshawky.theAir.ui.base.BaseViewModel
import eg.mahmoudshawky.theAir.utils.NetworkState
import eg.mahmoudshawky.theAir.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class TvShowDetailsViewModel(private val repository: Repository, private val tvShow: TvShow) :
    BaseViewModel(repository) {

    private val _rateNetworkState = SingleLiveEvent<NetworkState>()
    val rateNetworkState: LiveData<NetworkState> get() = _rateNetworkState

    private val _posterUrlLiveData = MutableLiveData<String>()
    val posterUrlLiveData: LiveData<String> get() = _posterUrlLiveData

    private val _titleLiveData = MutableLiveData<String>()
    val titleLiveData: LiveData<String> get() = _titleLiveData

    private val _overviewLiveData = MutableLiveData<String>()
    val overviewLiveData: LiveData<String> get() = _overviewLiveData

    private val _homePageLiveData = MutableLiveData<String>()
    val homePageLiveData: LiveData<String> get() = _homePageLiveData

    private val _epiNoLiveData = MutableLiveData<Int>()
    val epiNoLiveData: LiveData<Int> get() = _epiNoLiveData

    private val _ratingLiveData = MutableLiveData<Float>()
    val ratingLiveData: LiveData<Float> get() = _ratingLiveData

    private val _genresLiveData = MutableLiveData<List<Genre>>()
    val genresLiveData: LiveData<List<Genre>> get() = _genresLiveData

    private val _networksLiveData = MutableLiveData<List<Network>>()
    val networksLiveData: LiveData<List<Network>> get() = _networksLiveData

    private val _castsLiveData = MutableLiveData<List<Cast>>()
    val castsLiveData: LiveData<List<Cast>> get() = _castsLiveData

    init {
        fetchTvShowDetails()
        tvShow.run {
            _titleLiveData.value = name
            _overviewLiveData.value = overview
            _posterUrlLiveData.value = poster_path
            _ratingLiveData.value = vote_average
        }
    }

    fun fetchTvShowDetails() {
        viewModelScope.launch(handler) {
            _networkState.postValue(NetworkState.LOADING)
            when (val result = repository.fetchTvShowDetails(tvShow.id)) {
                is Result.Success -> {
                    if (result.data != null) {
                        _networkState.postValue(NetworkState.LOADED)
                        setDetailsData(result.data)
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

    private fun setDetailsData(data: TvShowDetailsResponse) {
        data.run {
            _titleLiveData.value = name
            _overviewLiveData.value = overview
            _posterUrlLiveData.value = poster_path
            _ratingLiveData.value = vote_average
            _castsLiveData.value = credits.cast
            _epiNoLiveData.value = number_of_episodes
            _genresLiveData.value = genres
            _networksLiveData.value = networks
            _homePageLiveData.value = homepage
        }
    }

    fun rateTvShow(rate: Float) {
        _rateNetworkState.value = NetworkState.LOADING
        viewModelScope.launch(handler) {
            when (val sessionResult = getGuestSessionId()) {
                is Result.Success -> {
                    sessionResult.data?.guest_session_id?.let {
                        when (val result =
                            repository.rateTvShow(tvShow.id, it, RatingRequest(rate))) {
                            is Result.Success -> _rateNetworkState.postValue(NetworkState.LOADED)
                            is Result.Error -> _rateNetworkState.postValue(NetworkState.error(result.exception.msg))
                        }
                    }
                }
                is Result.Error -> {
                    _rateNetworkState.postValue(NetworkState.error(sessionResult.exception.msg))
                }
            }

        }
    }

}