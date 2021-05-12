package id.chainlizard.saltiesmovie.functions

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import id.chainlizard.saltiesmovie.data.model.MovieDetailMod
import id.chainlizard.saltiesmovie.data.model.MovieDiscoverMod
import id.chainlizard.saltiesmovie.data.model.TVDetailMod
import id.chainlizard.saltiesmovie.data.model.TVDiscoverMod

object MyObj {

    enum class RequestType {
        discoverMovie, discoverTV, detailMovie, detailTV
    }
    enum class pageType{
        watchList, Discover
    }

    fun isOnline(activity: Activity): Boolean {
        val myConnectivity =
            activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = myConnectivity.activeNetworkInfo
        return network?.isConnected ?: false
    }

    fun writeIdPreference(id: Int, activity: Activity) {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putInt("id", id)
            apply()
        }
    }

    fun readIdPreference(activity: Activity): Int {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getInt("id", 1)
    }

    fun parseJson(data: String, type: RequestType): Any {
        val gson = Gson()
        if (type == RequestType.discoverMovie) {
            return gson.fromJson(data, MovieDiscoverMod.MoviesPage::class.java)
        } else if (type == RequestType.discoverTV) {
            return gson.fromJson(data, TVDiscoverMod.TVPage::class.java)
        } else if (type == RequestType.detailMovie) {
            return gson.fromJson(data, MovieDetailMod.MovieDetail::class.java)
        } else if (type == RequestType.detailTV) {
            return gson.fromJson(data, TVDetailMod.TVDetail::class.java)
        } else {
            return 0
        }
    }

    fun buildMyErrorDialog(context: Context, pesan: String) {
        MaterialAlertDialogBuilder(context)
            .setTitle("Network Error")
            .setMessage(pesan)
            .setPositiveButton("Ok") { dialog, which -> }
            .show()
    }

    data class Genre(
        val id: Int,
        val name: String
    )

    data class ProductionCompanies(
        val id: Int,
        val logo_path: String,
        val name: String,
        val origin_country: String
    )

    data class Country(
        val iso_3166_1: String,
        val name: String
    )

    data class Language(
        val english_name: String,
        val iso_639_1: String,
        val name: String
    )

    data class Creator(
        val id: Int,
        val crecredit_id: String,
        val name: String,
        val profile_path: String
    )

    data class Episode(
        val air_date: String,
        val episode_number: Int,
        val id: Int,
        val name: String,
        val overview: String,
        val production_code: String,
        val season_number: Int,
        val still_path: String,
        val vote_average: Double,
        val vote_count: Int
    )

    data class Network(
        val name: String,
        val id: Int,
        val logo_path: String,
        val origin_country: String
    )

    data class Season(
        val air_date: String,
        val episode_count: Int,
        val id: Int,
        val name: String,
        val overview: String,
        val poster_path: String,
        val season_number: Int
    )

}