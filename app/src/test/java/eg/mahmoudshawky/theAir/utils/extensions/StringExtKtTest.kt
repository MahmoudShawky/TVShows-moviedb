package eg.mahmoudshawky.theAir.utils.extensions

import eg.mahmoudshawky.theAir.utils.RESPONSES_FULL_DATE_FORMAT
import org.checkerframework.checker.signedness.qual.Constant
import org.junit.Assert.*
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class StringExtKtTest {

    @Test
    fun isDateExpired() {
    }

    @Test
    fun isDateExpired_ExpiredDate_ReturnsTrue() {
        assertTrue("2020-08-27 16:26:40 UTC".isDateExpired())
    }

    @Test
    fun isDateExpired_ComingDate_ReturnsFalse() {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 1)
        assertFalse(SimpleDateFormat( RESPONSES_FULL_DATE_FORMAT, Locale.ENGLISH).format(cal.time).isDateExpired())
    }

    @Test
    fun isDateExpired_SameDate_ReturnsTrue() {
        assertTrue(SimpleDateFormat( RESPONSES_FULL_DATE_FORMAT, Locale.ENGLISH).format(Date()).isDateExpired())
    }
}