package eg.mahmoudshawky.theAir.data.models.network

import androidx.annotation.Keep
import eg.mahmoudshawky.theAir.utils.SimpleImageText

@Keep
data class CreatedBy(
    val credit_id: String,
    val gender: Int,
    val id: Int,
    val name: String,
    val profile_path: String?
) : SimpleImageText {
    override fun getText() = name
    override fun getImagePath() = profile_path
}