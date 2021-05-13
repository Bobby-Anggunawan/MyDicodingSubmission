package id.chainlizard.saltiesmovie.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.chainlizard.saltiesmovie.functions.MyObj

object MovieDetailMod {
    fun convertToFav(real: MovieDetail): MovieFavoriteDetail {
        return MovieFavoriteDetail(
                real.id,
                real.poster_path,
                real.title,
                real.tagline,
                real.overview,
                real.vote_average,
                real.release_date,
                real.original_language,
                real.status,
                real.budget,
                real.revenue
        )
    }

    fun convertToListDis(real: List<MovieFavoriteDetail>): List<MovieDiscoverMod.MoviePage_List> {
        val ret = arrayListOf<MovieDiscoverMod.MoviePage_List>()
        real.forEach {
            ret.add(
                    MovieDiscoverMod.MoviePage_List(
                            true,
                            "",
                            arrayListOf(),
                            it.id,
                            it.original_language,
                            it.title,
                            it.overview,
                            0.0,
                            it.poster_path,
                            it.release_date,
                            it.title,
                            true,
                            it.vote_average,
                            1
                    )
            )
        }
        return ret
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

    @Entity
    data class MovieFavoriteDetail(
            @PrimaryKey val id: Int,
            val poster_path: String,
            val title: String,
            val tagline: String,
            val overview: String,
            val vote_average: Double,
            val release_date: String,
            val original_language: String,
            val status: String,
            val budget: Int,
            val revenue: Int
    )
}