package eg.mahmoudshawky.theAir.data.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BaseResponse<T>(
    @SerializedName("status")
    val status: Int,
    @SerializedName("body")
    val body: T?,
    @SerializedName("error")
    val error: ApiError?
)

@Keep
data class ApiError(
    @SerializedName("timestamp")
    val timestamp: String,
    @SerializedName("path")
    val path: String,
    @SerializedName("message")
    val message: List<String>
)