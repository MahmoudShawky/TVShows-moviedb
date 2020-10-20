package eg.mahmoudshawky.theAir.data.remote

import eg.mahmoudshawky.theAir.data.models.network.RatingRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

/***
 * @author mahmoud.shawky
 *
 * The implementation of {@link RemoteRepository}
 */
class RemoteRepositoryImp(private val serviceApi: ServiceApi) : RemoteRepository {

    override suspend fun fetchLatestTvShows(page: Int) = withContext(Dispatchers.IO) {
        safeApiResult(serviceApi.fetchLatestTvShows(page))
    }

    override suspend fun fetchTvShowDetails(tvId: Int) = withContext(Dispatchers.IO) {
        safeApiResult(serviceApi.fetchTvShowDetails(tvId))
    }

    override suspend fun fetchGuestSessionId() = withContext(Dispatchers.IO) {
        safeApiResult(serviceApi.fetchGuestSessionId())
    }

    override suspend fun rateTvShow(tvId: Int, guestSessionId: String, request: RatingRequest) =
        withContext(Dispatchers.IO) {
            safeApiResult(serviceApi.rateTvShow(tvId, guestSessionId, request))
        }

    override suspend fun fetchRecommendationTv(tvId: Int, page: Int) = withContext(Dispatchers.IO) {
        safeApiResult(serviceApi.fetchRecommendationsTv(tvId, page))
    }

    override suspend fun fetchSimilarTv(tvId: Int, page: Int) = withContext(Dispatchers.IO) {
        safeApiResult(serviceApi.fetchSimilarTv(tvId, page))
    }


    private fun <T> safeApiResult(call: Response<T>): Result<T> {
        if (call.isSuccessful) return Result.Success(call.body())

        when (call.code()) {
            500, 504 -> throw Failure.ServerException(call.message())
            404 -> throw Failure.NotFoundException(call.message())
            401 -> throw Failure.InvalidAPIKeyException(call.message())
            else -> {
                return Result.Error(Failure.UnknownException(call.message()))
            }
        }
    }
}