package id.chainlizard.saltiesmovie.viewmodel

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.chainlizard.saltiesmovie.functions.MyObj
import id.chainlizard.saltiesmovie.model.MovieDiscoverMod
import id.chainlizard.saltiesmovie.functions.MyIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieDiscoverVM: ViewModel() {

    private var movies: MutableLiveData<ArrayList<MovieDiscoverMod.MoviePage_List>>? = null

    fun getMovie(context: Context): LiveData<ArrayList<MovieDiscoverMod.MoviePage_List>> {
        if(movies == null){
            movies = MutableLiveData()
            loadMovies(1, context)
        }
        return movies as MutableLiveData<ArrayList<MovieDiscoverMod.MoviePage_List>>
    }

    fun loadMovies(page: Int, context: Context){
        MyIdlingResource.increment()
        GlobalScope.launch(Dispatchers.Default){
            try{
                val alist = MovieDiscoverMod.getData(page)
                movies?.postValue(alist.results)
            }
            catch (e: Exception){
                Handler(Looper.getMainLooper()).post {
                    MyObj.buildMyErrorDialog(context, e.message.toString())
                }
                MyIdlingResource.decrement()
            }
        }
    }
}