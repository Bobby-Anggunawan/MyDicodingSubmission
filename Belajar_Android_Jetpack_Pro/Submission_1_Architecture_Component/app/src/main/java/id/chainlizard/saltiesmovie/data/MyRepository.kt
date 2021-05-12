package id.chainlizard.saltiesmovie.data

import android.util.Log
import androidx.paging.DataSource
import androidx.room.Room
import id.chainlizard.saltiesmovie.data.model.MovieDetailMod
import id.chainlizard.saltiesmovie.data.model.MovieDiscoverMod
import id.chainlizard.saltiesmovie.data.model.TVDetailMod
import id.chainlizard.saltiesmovie.data.model.TVDiscoverMod
import id.chainlizard.saltiesmovie.data.pagingsource.MovieDiscoverPS
import id.chainlizard.saltiesmovie.data.pagingsource.TVDiscoverPS
import id.chainlizard.saltiesmovie.functions.MyDatabase
import id.chainlizard.saltiesmovie.functions.MyObj
import id.chainlizard.saltiesmovie.functions.Networking
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyRepository @Inject constructor(private var myNetwork: Networking.MyNetwork, private var db: MyDatabase.AppDatabase) {

    val moviePS = MovieDiscoverPS(this, MyObj.pageType.Discover)
    val movieWatchListPS = MovieDiscoverPS(this, MyObj.pageType.watchList)
    val tvPS = TVDiscoverPS(this)
    val myDB = db.myDB()

    fun getMovieDetail(id: Int): MovieDetailMod.MovieDetail {
        var textJSON = ""
        myNetwork.movieDetailReq.baseUrl("https://api.themoviedb.org/3/movie/" + id.toString())
        runBlocking {
            val getFromApi = async(Dispatchers.IO) { myNetwork.movieDetailReq.runSync() }
            textJSON = getFromApi.await()
        }
        return MyObj.parseJson(
            textJSON,
            MyObj.RequestType.detailMovie
        ) as MovieDetailMod.MovieDetail
    }

    fun getMovieDiscover(page: Int): MovieDiscoverMod.MoviesPage {
        myNetwork.movieDiscoverReq.addQueryParamater("page", "$page")
        val textJSON =  myNetwork.movieDiscoverReq.runSync()

        return MyObj.parseJson(
            textJSON,
            MyObj.RequestType.discoverMovie
        ) as MovieDiscoverMod.MoviesPage
    }

    fun getTVDetail(id: Int): TVDetailMod.TVDetail {
        var textJSON = ""
        myNetwork.tvDetailReq.baseUrl("https://api.themoviedb.org/3/tv/" + id.toString())
        runBlocking {
            val getFromApi = async(Dispatchers.IO) { myNetwork.tvDetailReq.runSync() }
            textJSON = getFromApi.await()
        }
        return MyObj.parseJson(textJSON, MyObj.RequestType.detailTV) as TVDetailMod.TVDetail
    }

    fun getTVDiscover(page: Int): TVDiscoverMod.TVPage {
        var textJSON = ""
        myNetwork.tvDiscoverReq.addQueryParamater("page", "$page")
        runBlocking {
            val getFromApi = async(context = Dispatchers.IO) { myNetwork.tvDiscoverReq.runSync() }
            textJSON = getFromApi.await()
        }
        return MyObj.parseJson(textJSON, MyObj.RequestType.discoverTV) as TVDiscoverMod.TVPage
    }

    fun getMovieWLAsc(page: Int): List<MovieDetailMod.MovieFavoriteDetail>{
        lateinit var waitRes: List<MovieDetailMod.MovieFavoriteDetail>
        runBlocking {
            val getDB = async(context = Dispatchers.IO) { myDB.getMovieAsc(page) }
            waitRes = getDB.await()
        }
        return waitRes
    }
    fun countMovieWL(): Int{
        var waitRes: Int
        runBlocking {
            val getDB = async(context = Dispatchers.IO) { myDB.countMovie() }
            waitRes = getDB.await()
        }
        return waitRes
    }
    fun movieExist(id: Int): Boolean{
        var waitRes: Boolean
        runBlocking {
            val getDB = async(context = Dispatchers.IO) { myDB.movieExists(id) }
            waitRes = getDB.await()
        }
        return waitRes
    }
    fun addMovie(movie: MovieDetailMod.MovieFavoriteDetail){
        GlobalScope.launch(Dispatchers.Default){
            myDB.insertMovie(movie)
        }
    }
    fun removeMovie(movie: MovieDetailMod.MovieFavoriteDetail){
        GlobalScope.launch(Dispatchers.Default){
            myDB.deleteMovie(movie)
        }
    }
}