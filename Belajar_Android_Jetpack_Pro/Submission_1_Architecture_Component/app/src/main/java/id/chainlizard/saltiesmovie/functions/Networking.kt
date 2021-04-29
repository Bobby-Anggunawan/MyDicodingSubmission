package id.chainlizard.saltiesmovie.functions

import android.content.Context
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.SyncHttpClient
import cz.msebera.android.httpclient.Header
import id.chainlizard.saltiesmovie.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import org.json.JSONArray


object Networking {
    class MyNetwork{
        var movieDetailReq: NetworkBuilder
        var movieDiscoverReq: NetworkBuilder
        var tvDetailReq: NetworkBuilder
        var tvDiscoverReq: NetworkBuilder

        init {
            movieDetailReq = NetworkBuilder()
                    .addQueryParamater("api_key", BuildConfig.apiKey)
                    .addQueryParamater("language", "en-US")
                    .addHeader("User-Agent", "request")
                    .addHeader("Authorization", "Bearer ${BuildConfig.apiReadAccessToken}")
                    .addHeader("Content-Type", "application/json;charset=utf-8")
            movieDiscoverReq = NetworkBuilder()
                    .baseUrl("https://api.themoviedb.org/3/discover/movie")
                    .addQueryParamater("api_key", BuildConfig.apiKey)
                    .addHeader("User-Agent", "request")
                    .addHeader("Authorization", "Bearer ${BuildConfig.apiReadAccessToken}")
                    .addHeader("Content-Type", "application/json;charset=utf-8")
                    .addQueryParamater("language", "en-US")
                    .addQueryParamater("sort_by", "popularity.desc")
                    .addQueryParamater("include_adult", "false")
                    .addQueryParamater("include_video", "false")
                    .addQueryParamater("with_watch_monetization_types", "flatrate")
            tvDetailReq = NetworkBuilder()
                    .addQueryParamater("api_key", BuildConfig.apiKey)
                    .addQueryParamater("language", "en-US")
                    .addHeader("User-Agent", "request")
                    .addHeader("Authorization", "Bearer ${BuildConfig.apiReadAccessToken}")
                    .addHeader("Content-Type", "application/json;charset=utf-8")
            tvDiscoverReq = NetworkBuilder()
                    .baseUrl("https://api.themoviedb.org/3/discover/tv")
                    .addQueryParamater("api_key", BuildConfig.apiKey)
                    .addHeader("User-Agent", "request")
                    .addHeader("Authorization", "Bearer ${BuildConfig.apiReadAccessToken}")
                    .addHeader("Content-Type", "application/json;charset=utf-8")
                    .addQueryParamater("language", "en-US")
                    .addQueryParamater("sort_by", "popularity.desc")
                    .addQueryParamater("timezone", "America%2FNew_York")
                    .addQueryParamater("include_null_first_air_dates", "false")
                    .addQueryParamater("with_watch_monetization_types", "flatrate")
        }
    }
    class NetworkBuilder{
        private data class headerCon(var name: String, var value: String)

        private var myBaseUrl = ""
        private var queryParamString = "?"
        private val headerList = arrayListOf<headerCon>()

        fun baseUrl(url: String): NetworkBuilder{
            myBaseUrl = url
            return this
        }
        fun addHeader(name: String, value: String): NetworkBuilder{
            headerList.add(headerCon(name, value))
            return this
        }
        fun addQueryParamater(name: String, value: String): NetworkBuilder{
            if(queryParamString == "?"){
                queryParamString += "$name=$value"
            }
            else{
                queryParamString += "&$name=$value"
            }
            return this
        }
        fun runSync(): String{
            val url = myBaseUrl+queryParamString
            val client = OkHttpClient()
            val request = Request.Builder().url(url)
            headerList.forEach {
                request.addHeader(it.name, it.value)
            }
            client.newCall(request.build()).execute().use { response -> return response.body!!.string() }
        }
    }
}