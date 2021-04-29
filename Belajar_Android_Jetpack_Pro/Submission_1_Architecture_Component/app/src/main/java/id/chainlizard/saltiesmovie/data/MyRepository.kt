package id.chainlizard.saltiesmovie.data

import android.content.Context
import id.chainlizard.saltiesmovie.functions.MyObj
import id.chainlizard.saltiesmovie.functions.Networking
import id.chainlizard.saltiesmovie.data.model.MovieDetailMod
import id.chainlizard.saltiesmovie.data.model.MovieDiscoverMod
import id.chainlizard.saltiesmovie.data.model.TVDetailMod
import id.chainlizard.saltiesmovie.data.model.TVDiscoverMod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyRepository @Inject constructor(private var myNetwork: Networking.MyNetwork) {
    fun getMovieDetail(id: Int): MovieDetailMod.MovieDetail {
        var textJSON = ""
        myNetwork.movieDetailReq.baseUrl("https://api.themoviedb.org/3/movie/"+id.toString())
        runBlocking {
            val getFromApi = async(Dispatchers.IO) { myNetwork.movieDetailReq.runSync() }
            textJSON = getFromApi.await()
        }
        return MyObj.parseJson(textJSON, MyObj.RequestType.detailMovie) as MovieDetailMod.MovieDetail
    }
    fun getMovieDiscover(page: Int): MovieDiscoverMod.MoviesPage{
        var textJSON = ""
        myNetwork.movieDiscoverReq.addQueryParamater("page", "$page")
        runBlocking {
            val getFromApi = async(Dispatchers.IO) { myNetwork.movieDiscoverReq.runSync() }
            textJSON = getFromApi.await()
        }
        return MyObj.parseJson(textJSON, MyObj.RequestType.discoverMovie) as MovieDiscoverMod.MoviesPage
    }
    fun getTVDetail(id: Int): TVDetailMod.TVDetail {
        var textJSON = ""
        myNetwork.tvDetailReq.baseUrl("https://api.themoviedb.org/3/tv/"+id.toString())
        runBlocking {
            val getFromApi = async(Dispatchers.IO) { myNetwork.tvDetailReq.runSync() }
            textJSON = getFromApi.await()
        }
        return MyObj.parseJson(textJSON, MyObj.RequestType.detailTV) as TVDetailMod.TVDetail
    }
    fun getTVDiscover(page: Int): TVDiscoverMod.TVPage{
        var textJSON = ""
        myNetwork.tvDiscoverReq.addQueryParamater("page", "$page")
        runBlocking {
            val getFromApi = async(context = Dispatchers.IO) { myNetwork.tvDiscoverReq.runSync() }
            textJSON = getFromApi.await()
        }
        return MyObj.parseJson(textJSON, MyObj.RequestType.discoverTV) as TVDiscoverMod.TVPage
    }
}