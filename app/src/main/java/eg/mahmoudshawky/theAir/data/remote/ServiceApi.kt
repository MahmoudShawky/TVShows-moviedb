package eg.mahmoudshawky.theAir.data.remote

import eg.mahmoudshawky.theAir.data.models.network.GuestSessionResponse
import eg.mahmoudshawky.theAir.data.models.network.LatestTvShowsResponse
import eg.mahmoudshawky.theAir.data.models.network.RatingRequest
import eg.mahmoudshawky.theAir.data.models.network.TvShowDetailsResponse
import eg.mahmoudshawky.theAir.utils.extensions.getApiDateFormatted
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface ServiceApi {

    /***
     * see https://developers.themoviedb.org/3/discover/tv-discover
     * Discover TV shows by different types of data
     *
     * To get latest TV shows we discover by first air date desc
     * and first air date less than or equal today
     *
     *  @param [page] the page of results to query.
     *  @param [firstAirDate] Filter and only include TV shows that have a original air date that is less than or equal to the specified value.
     *                          default value is today date
     */
//    @GET("/3/tv/popular")
    @GET("/3/discover/tv?sort_by=first_air_date.desc")
    suspend fun fetchLatestTvShows(
        @Query("page") page: Int = 1,
        @Query("first_air_date.lte") firstAirDate: String = Date().getApiDateFormatted()
    ): Response<LatestTvShowsResponse>


    /***
     * see https://developers.themoviedb.org/3/tv/get-tv-details
     * Get the primary TV show details by id.
     *
     *  @param [tvId] TV show Id.
     *  @param [appendParams] append_to_response for extra data to be retrieved .
     */
    @GET("/3/tv/{tv_id}")
    suspend fun fetchTvShowDetails(
        @Path("tv_id") tvId: Int,
        @Query("append_to_response") appendParams: String = "credits"
    ): Response<TvShowDetailsResponse>


    /***
     * see https://developers.themoviedb.org/3/tv/rate-tv-show
     * Rate a TV show.
     *
     *  @param [tvId] TV show Id.
     */
    @POST("/3/tv/{tv_id}/rating")
    suspend fun rateTvShow(
        @Path("tv_id") tvId: Int,
        @Query("guest_session_id") guestSessionId: String,
        @Body request: RatingRequest
    ): Response<TvShowDetailsResponse>


    /***
     * see https://developers.themoviedb.org/3/authentication/create-guest-session
     *
     * This method will let us create a new guest session.
     * Guest sessions are a type of session that will let a user rate movies and TV shows
     * but not require them to have a TMDb user account.
     *
     */
    @GET("/3/authentication/guest_session/new")
    suspend fun fetchGuestSessionId(): Response<GuestSessionResponse>

    /***
     * see https://developers.themoviedb.org/3/tv/get-tv-recommendations
     * Get the list of TV show recommendations for this TV Show.
     *
     *
     *  @param [page] the page of results to query.
     *  @param [tvId] TV show Id.
     */
    @GET("/3/tv/{tv_id}/recommendations")
    suspend fun fetchRecommendationsTv(
        @Path("tv_id") tvId: Int,
        @Query("page") page: Int = 1
    ): Response<LatestTvShowsResponse>


    /***
     * see https://developers.themoviedb.org/3/tv/get-tv-recommendations
     * Get the list of TV show recommendations for this TV Show.
     *
     *
     *  @param [page] the page of results to query.
     *  @param [tvId] TV show Id.
     */
    @GET("/3/tv/{tv_id}/similar")
    suspend fun fetchSimilarTv(
        @Path("tv_id") tvId: Int,
        @Query("page") page: Int = 1
    ): Response<LatestTvShowsResponse>
}