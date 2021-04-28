package id.chainlizard.saltiesmovie.model

import id.chainlizard.saltiesmovie.functions.MyObj
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

object MovieDiscoverMod {
    fun getData(page: Int): MoviesPage {
        var textJSON = ""
        runBlocking {
            val net = MyObj.initNetwork("https://api.themoviedb.org/3/discover/movie")
                    .addQueryParamater("language", "en-US")
                    .addQueryParamater("sort_by", "popularity.desc")
                    .addQueryParamater("include_adult", "false")
                    .addQueryParamater("include_video", "false")
                    .addQueryParamater("page", "$page")
                    .addQueryParamater("with_watch_monetization_types", "flatrate")

            val getFromApi = async(context = Dispatchers.IO) { net.runSync() }
            textJSON = getFromApi.await()
        }
        return MyObj.parseJson(textJSON, MyObj.RequestType.discoverMovie) as MoviesPage
    }

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