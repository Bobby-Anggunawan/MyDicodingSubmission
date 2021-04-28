package id.chainlizard.saltiesmovie.model

import id.chainlizard.saltiesmovie.functions.MyObj
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

object MovieDetailMod {
    fun getData(id: Int): MovieDetail {
        var textJSON = ""
        runBlocking {
            val net = MyObj.initNetwork("https://api.themoviedb.org/3/movie/"+id.toString())
                    .addQueryParamater("language", "en-US")

            val getFromApi = async(context = Dispatchers.IO) { net.runSync() }
            textJSON = getFromApi.await()
        }
        return MyObj.parseJson(textJSON, MyObj.RequestType.detailMovie) as MovieDetail
    }

    data class MovieDetail(
            val adult: Boolean,
            val backdrop_path: String,
            val belongs_to_collection: Any,
            val budget: Int,
            val genres: ArrayList<MyObj.Genre>,
            val homepage: String,
            val id: Int,
            val imdb_id: String,
            val original_language: String,
            val original_title: String,
            val overview: String,
            val popularity: Double,
            val poster_path: String,
            val production_companies: ArrayList<MyObj.ProductionCompanies>,
            val production_countries: ArrayList<MyObj.Country>,
            val release_date: String,
            val revenue: Int,
            val runtime: Int,
            val spoken_languages: ArrayList<MyObj.Language>,
            val status: String,
            val tagline: String,
            val title: String,
            val video: Boolean,
            val vote_average: Double,
            val vote_count: Int
    )
}