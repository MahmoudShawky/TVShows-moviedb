package eg.mahmoudshawky.theAir.utils.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast


//Context
@Suppress("DEPRECATION")
fun Context.isNetworkConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetworkInfo
    return activeNetwork != null && activeNetwork.isConnectedOrConnecting
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
//End of Context