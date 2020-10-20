package eg.mahmoudshawky.theAir.data.models.network

import androidx.annotation.Keep
import eg.mahmoudshawky.theAir.utils.SimpleText

@Keep
data class Genre(
    val id: Int,
    val name: String
): SimpleText {
    override fun getText() = name
}