package eg.mahmoudshawky.theAir.data.local.prefs

interface PreferencesHelper {
    fun setGuestSession(sessionId: String)
    fun getGuestSession(): String?
    fun setGuestSessionExpiration(expiration: String)
    fun getGuestSessionExpiration(): String?
}