package eg.mahmoudshawky.theAir.utils.extensions

import eg.mahmoudshawky.theAir.utils.RESPONSES_FULL_DATE_FORMAT
import java.text.SimpleDateFormat
import java.util.*

fun String.isDateExpired(): Boolean {
    val date = SimpleDateFormat(
        RESPONSES_FULL_DATE_FORMAT, Locale.ENGLISH
    ).parse(this)

    return date?.before(Date()) ?: false
}