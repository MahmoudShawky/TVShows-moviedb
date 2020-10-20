package eg.mahmoudshawky.theAir.utils

import android.util.Log

object AppLogger {

    fun i(tag: String, msg: String, t: Throwable? = null) {
        Log.i(tag, msg, t)
    }

    fun d(tag: String, msg: String, t: Throwable? = null) {
        Log.d(tag, msg, t)
    }

    fun v(tag: String, msg: String, t: Throwable? = null) {
        Log.v(tag, msg, t)
    }

    fun e(tag: String, msg: String, t: Throwable? = null) {
        Log.e(tag, msg, t)
    }

    fun w(tag: String, msg: String, t: Throwable? = null) {
        Log.w(tag, msg, t)
    }

    fun w(tag: String, t: Throwable? = null) {
        Log.w(tag, t)
    }
}