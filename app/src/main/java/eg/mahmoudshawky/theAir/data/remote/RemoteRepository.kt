package eg.mahmoudshawky.theAir.data.remote

import eg.mahmoudshawky.theAir.data.models.network.GuestSessionResponse
import eg.mahmoudshawky.theAir.data.models.network.LatestTvShowsResponse
import eg.mahmoudshawky.theAir.data.models.network.RatingRequest
import eg.mahmoudshawky.theAir.data.models.network.TvShowDetailsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.Query

/***
 * @author mahmoud.shawky
 *
 * RemoteRepo is an interface that should be implemented to be able to interact with the Remote service functions
 */
interface RemoteRepository {
    suspend fun fetchLatestTvShows(page: Int): Result<LatestTvShowsResponse>
    suspend fun fetchTvShowDetails(tvId: Int): Result<TvShowDetailsResponse>
    suspend fun fetchGuestSessionId(): Result<GuestSessionResponse>
    suspend fun rateTvShow(tvId: Int, guestSessionId: String, request: RatingRequest): Result<TvShowDetailsResponse>
    suspend fun fetchRecommendationTv(tvId: Int, page: Int = 1): Result<LatestTvShowsResponse>
    suspend fun fetchSimilarTv(tvId: Int, page: Int = 1): Result<LatestTvShowsResponse>
}