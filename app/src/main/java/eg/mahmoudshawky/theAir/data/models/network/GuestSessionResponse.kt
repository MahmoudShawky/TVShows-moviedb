package eg.mahmoudshawky.theAir.data.models.network

import androidx.annotation.Keep

@Keep
data class GuestSessionResponse(
    val expires_at: String,
    val guest_session_id: String,
    val success: Boolean
)