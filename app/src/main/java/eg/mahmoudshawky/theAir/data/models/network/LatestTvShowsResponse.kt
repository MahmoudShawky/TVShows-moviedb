package eg.mahmoudshawky.theAir.data.models.network

import androidx.annotation.Keep
import eg.mahmoudshawky.theAir.data.models.entity.TvShow

@Keep
data class LatestTvShowsResponse(
    val page: Int,
    val results: List<TvShow>,
    val total_pages: Int,
    val total_results: Int
)