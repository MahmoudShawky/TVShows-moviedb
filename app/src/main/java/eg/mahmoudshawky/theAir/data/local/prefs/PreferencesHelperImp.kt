package eg.mahmoudshawky.theAir.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import eg.mahmoudshawky.theAir.utils.extensions.getAPIFullDateFormatted
import java.util.*

class PreferencesHelperImp(private val context: Context) : PreferencesHelper {

    private val prefManager: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    companion object {
        private const val GUEST_SESSION_ID_PREF = "guest_session_id"
        private const val SESSION_EXPIRES_AT_PREF = "session_expires_at"
    }

    override fun setGuestSession(sessionId: String) {
        setStringPreference(GUEST_SESSION_ID_PREF, sessionId)
    }

    override fun getGuestSession() = getStringPreference(GUEST_SESSION_ID_PREF, null)


    override fun setGuestSessionExpiration(expiration: String) {
        setStringPreference(SESSION_EXPIRES_AT_PREF, expiration)
    }

    override fun getGuestSessionExpiration() =
        getStringPreference(SESSION_EXPIRES_AT_PREF, null)

    private fun setStringPreference(key: String, value: String) {
        prefManager.edit().putString(key, value)
            .apply()
    }

    private fun getStringPreference(key: String, defaultValue: String?): String {
        return prefManager
            .getString(key, defaultValue) ?: ""
    }

}