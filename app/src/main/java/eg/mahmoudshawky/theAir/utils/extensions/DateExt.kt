package eg.mahmoudshawky.theAir.utils.extensions

import eg.mahmoudshawky.theAir.utils.API_DATE_FORMAT
import eg.mahmoudshawky.theAir.utils.RESPONSES_FULL_DATE_FORMAT
import java.text.SimpleDateFormat
import java.util.*

/***Start of Date*/
fun Date.getApiDateFormatted(): String = SimpleDateFormat(API_DATE_FORMAT, Locale.ENGLISH).format(time)

fun Date.getAPIFullDateFormatted(): String = SimpleDateFormat(RESPONSES_FULL_DATE_FORMAT, Locale.ENGLISH).format(time)
/***End of Date*/
