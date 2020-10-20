package eg.mahmoudshawky.theAir.utils

import eg.mahmoudshawky.theAir.data.models.entity.TvShow
import eg.mahmoudshawky.theAir.data.models.network.LatestTvShowsResponse

class TestData {

    companion object {
        val mockTvShow = TvShow(
            poster_path = "/vC324sdfcS313vh9QXwijLIHPJp.jpg",
            popularity = 47.432451F,
            id = 31917,
            backdrop_path = "/rQGBjWNveVeF8f2PGRtS85w9o9r.jpg",
            vote_average = 5.04F,
            overview = "Based on the Pretty Little Liars series of young adult novels by Sara Shepard, the series follows the lives of four girls — Spencer, Hanna, Aria, and Emily — whose clique falls apart after the disappearance of their queen bee, Alison. One year later, they begin receiving messages from someone using the name \"A\" who threatens to expose their secrets — including long-hidden ones they thought only Alison knew.",
            first_air_date = "2010-06-08",
            origin_country = listOf("US"),
            genre_ids = listOf(18, 9648),
            original_language = "en",
            vote_count = 133,
            name = "Pretty Little Liars",
            original_name = "Pretty Little Liars"
        )

        val mockTvResponse = LatestTvShowsResponse(
            page = 1,
            results = listOf(mockTvShow),
            total_results = 20000,
            total_pages = 1000
        )

        val mockRecommendedTvResponse = LatestTvShowsResponse(
            page = 1,
            results = listOf(mockTvShow, mockTvShow),
            total_results = 20000,
            total_pages = 1000
        )

        val mockSimilarTvResponse = LatestTvShowsResponse(
            page = 1,
            results = listOf(mockTvShow, mockTvShow, mockTvShow),
            total_results = 20000,
            total_pages = 1000
        )
    }

}