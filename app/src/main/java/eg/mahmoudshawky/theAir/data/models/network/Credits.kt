package eg.mahmoudshawky.theAir.data.models.network

import androidx.annotation.Keep

@Keep
data class Credits(
    val cast: List<Cast>?,
    val crew: List<Crew>?
)