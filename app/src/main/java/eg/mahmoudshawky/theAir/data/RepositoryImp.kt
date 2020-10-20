package eg.mahmoudshawky.theAir.data

import eg.mahmoudshawky.theAir.data.local.db.DatabaseHelper
import eg.mahmoudshawky.theAir.data.local.prefs.PreferencesHelper
import eg.mahmoudshawky.theAir.data.models.network.RatingRequest
import eg.mahmoudshawky.theAir.data.remote.RemoteRepository

/***
 * @author mahmoud.shawky
 *
 * The implementation of {@link Repository}
 */
class RepositoryImp(
    private val remoteRepository: RemoteRepository,
    private val databaseHelper: DatabaseHelper,
    private val preferencesHelper: PreferencesHelper
) : Repository {

    //Remote
    override suspend fun fetchLatestTvShows(page: Int) = remoteRepository.fetchLatestTvShows(page)
    override suspend fun fetchTvShowDetails(tvId: Int) = remoteRepository.fetchTvShowDetails(tvId)
    override suspend fun fetchGuestSessionId() = remoteRepository.fetchGuestSessionId()

    override suspend fun rateTvShow(tvId: Int, guestSessionId: String, request: RatingRequest) =
        remoteRepository.rateTvShow(tvId, guestSessionId, request)

    override suspend fun fetchRecommendationTv(tvId: Int, page: Int) =
        remoteRepository.fetchRecommendationTv(tvId, page)

    override suspend fun fetchSimilarTv(tvId: Int, page: Int) =
        remoteRepository.fetchSimilarTv(tvId, page)

    //Preferences
    override fun setGuestSession(sessionId: String) = preferencesHelper.setGuestSession(sessionId)
    override fun getGuestSession() = preferencesHelper.getGuestSession()
    override fun setGuestSessionExpiration(expiration: String) =
        preferencesHelper.setGuestSessionExpiration(expiration)

    override fun getGuestSessionExpiration() = preferencesHelper.getGuestSessionExpiration()
}