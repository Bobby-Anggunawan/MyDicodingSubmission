package id.chainlizard.saltiesmovie.data.model

object MovieDiscoverMod {
    data class MoviePage_List(
            val adult: Boolean,
            val backdrop_path: String,
            val genre_ids: ArrayList<Int>,
            val id: Int,
            val original_language: String,
            val original_title: String,
            val overview: String,
            val popularity: Double,
            val poster_path: String,
            val release_date: String,
            val title: String,
            val video: Boolean,
            val vote_average: Double,
            val vote_count: Int
    )

    data class MoviesPage(
            val page: Int,
            val results: ArrayList<MoviePage_List>,
            val total_pages: Int,
            val total_results: Int
    )
}