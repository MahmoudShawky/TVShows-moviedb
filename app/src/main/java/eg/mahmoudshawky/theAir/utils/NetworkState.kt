package eg.mahmoudshawky.theAir.utils

import androidx.annotation.Keep
import eg.mahmoudshawky.theAir.data.remote.Failure

@Keep
enum class Status {
    RUNNING,
    SUCCESS,
    SUCCESSNoData,
    FAILED
}

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
    val status: Status,
    val msg: String? = null
) {
    companion object {
        val LOADED = NetworkState(Status.SUCCESS)
        val NO_DATA = NetworkState(Status.SUCCESSNoData)
        val LOADING = NetworkState(Status.RUNNING)
        fun error(msg: String?) = NetworkState(Status.FAILED, msg)
    }
}
