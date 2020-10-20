package eg.mahmoudshawky.theAir.data.models.network

import androidx.annotation.Keep
import eg.mahmoudshawky.theAir.utils.SimpleImageText

@Keep
data class Cast(
    val character: String,
    val credit_id: String,
    val gender: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val profile_path: String?
):SimpleImageText {
    override fun getText() = name
    override fun getImagePath() = profile_path
}