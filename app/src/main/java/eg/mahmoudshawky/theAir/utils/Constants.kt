package eg.mahmoudshawky.theAir.utils

import androidx.annotation.Keep


const val API_DATE_FORMAT = "yyyy-MM-dd"
const val RESPONSES_FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss z"


@Keep
enum class FetchType(type: Int) {
    LATEST(0),
    RECOMMENDED(1),
    SIMILAR(2)
}



