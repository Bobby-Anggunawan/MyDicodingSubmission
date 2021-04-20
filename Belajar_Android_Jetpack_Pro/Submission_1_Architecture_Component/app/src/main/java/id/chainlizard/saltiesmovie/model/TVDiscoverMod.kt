package id.chainlizard.saltiesmovie.model

import id.chainlizard.saltiesmovie.BuildConfig
import id.chainlizard.saltiesmovie.functions.MyObj
import id.chainlizard.saltiesmovie.functions.Networking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

object TVDiscoverMod {
    fun getData(page: Int): TVPage {
        var textJSON = ""
        runBlocking {
            val net = MyObj.initNetwork("https://api.themoviedb.org/3/discover/tv")
                    .addQueryParamater("language", "en-US")
                    .addQueryParamater("sort_by", "popularity.desc")
                    .addQueryParamater("page", "$page")
                    .addQueryParamater("timezone", "America%2FNew_York")
                    .addQueryParamater("include_null_first_air_dates", "false")
                    .addQueryParamater("with_watch_monetization_types", "flatrate")

            val getFromApi = async(context = Dispatchers.IO) { net.runSync() }
            textJSON = getFromApi.await()
        }
        return MyObj.parseJson(textJSON, MyObj.RequestType.discoverTV) as TVPage
    }

    data class TVPage_List(
        val backdrop_path: String,
        val first_air_date: String,
        val genre_ids: ArrayList<Int>,
        val id: Int,
        val name: String,
        val origin_country: ArrayList<String>,
        val original_language: String,
        val original_name: String,
        val overview: String,
        val popularity: Double,
        val poster_path: String,
        val vote_average: Double,
        val vote_count: Int
    )

    data class TVPage(
            val page: Int,
            val results: ArrayList<TVPage_List>,
            val total_pages: Int,
            val total_results: Int
    )
}