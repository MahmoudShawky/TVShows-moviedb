package eg.mahmoudshawky.theAir.data.models.network

import androidx.annotation.Keep
import eg.mahmoudshawky.theAir.utils.SimpleText

@Keep
data class Network(
    val id: Int,
    val logo_path: String,
    val name: String,
    val origin_country: String
) : SimpleText {
    override fun getText() = name
}