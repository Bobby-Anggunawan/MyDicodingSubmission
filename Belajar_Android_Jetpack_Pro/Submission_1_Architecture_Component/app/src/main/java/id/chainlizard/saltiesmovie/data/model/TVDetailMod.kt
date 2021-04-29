package id.chainlizard.saltiesmovie.data.model

import id.chainlizard.saltiesmovie.functions.MyObj

object TVDetailMod {
    data class TVDetail(
            val backdrop_path: String,
            val created_by: ArrayList<MyObj.Creator>,
            val episode_run_time: ArrayList<Int>,
            val first_air_date: String,
            val genres: ArrayList<MyObj.Genre>,
            val homepage: String,
            val id: Int,
            val in_production: Boolean,
            val languages: ArrayList<String>,
            val last_air_date: String,
            val last_episode_to_air: MyObj.Episode,
            val name: String,
            val next_episode_to_air: MyObj.Episode,
            val networks: ArrayList<MyObj.Network>,
            val number_of_episodes: Int,
            val number_of_seasons: Int,
            val origin_country: ArrayList<String>,
            val original_language: String,
            val original_name: String,
            val overview: String,
            val popularity: Double,
            val poster_path: String,
            val production_companies: ArrayList<MyObj.ProductionCompanies>,
            val production_countries: ArrayList<MyObj.Country>,
            val seasons: ArrayList<MyObj.Season>,
            val spoken_languages: ArrayList<MyObj.Language>,
            val status: String,
            val tagline: String,
            val type: String,
            val vote_average: Double,
            val vote_count: Int
    )
}