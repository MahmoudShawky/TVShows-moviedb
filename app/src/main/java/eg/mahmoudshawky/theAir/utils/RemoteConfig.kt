package eg.mahmoudshawky.theAir.utils

object RemoteConfig {

    //todo set values from API
    //Images URL
    //https://developers.themoviedb.org/3/configuration/get-api-configuration
    const val POSTER_BASE_URL_O = "https://image.tmdb.org/t/p/original"
    const val POSTER_BASE_URL_THUMB = "https://image.tmdb.org/t/p/w342"

    fun getThumbnailsUrl(path: String) = POSTER_BASE_URL_THUMB + path
    fun getOriginalUrl(path: String) = POSTER_BASE_URL_O + path
}