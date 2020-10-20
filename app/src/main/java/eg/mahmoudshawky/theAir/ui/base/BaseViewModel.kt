package eg.mahmoudshawky.theAir.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eg.mahmoudshawky.theAir.data.Repository
import eg.mahmoudshawky.theAir.data.models.network.GuestSessionResponse
import eg.mahmoudshawky.theAir.data.remote.Failure
import eg.mahmoudshawky.theAir.data.remote.Result
import eg.mahmoudshawky.theAir.utils.NetworkState
import eg.mahmoudshawky.theAir.utils.extensions.isDateExpired
import kotlinx.coroutines.CoroutineExceptionHandler

open class BaseViewModel(private val repository: Repository) : ViewModel() {

    internal val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> get() = _networkState

    internal val handler = CoroutineExceptionHandler { _, ex ->
        when(ex){
            is Failure.InvalidAPIKeyException ->{
                //todo show blocker dialog
                _networkState.postValue(NetworkState.error(ex.message ?: "Unknown Error"))
            }
            is Failure.UnknownException,
            is Failure.ServerException,
            is Failure.NotFoundException,
            is Failure.NetworkException ->{
                _networkState.postValue(NetworkState.error(ex.message ?: "Unknown Error"))
            }
            else ->{
                _networkState.value = NetworkState.error("Unknown Error")
            }
        }
    }

    suspend fun getGuestSessionId(): Result<GuestSessionResponse> {
        val guestSessionId = repository.getGuestSession()
        val sessionExpirationDate = repository.getGuestSessionExpiration()
        if (sessionExpirationDate.isNullOrEmpty() || sessionExpirationDate.isDateExpired()) {
            val sessionResult = repository.fetchGuestSessionId()
            if (sessionResult is Result.Success) {
                sessionResult.data?.let {
                    repository.run {
                        setGuestSession(it.guest_session_id)
                        setGuestSessionExpiration(it.expires_at)
                    }
                }
            }

            return sessionResult;
        }else{
            return Result.Success(guestSessionId?.let {
                GuestSessionResponse(sessionExpirationDate,
                    it, true)
            })
        }
    }
}