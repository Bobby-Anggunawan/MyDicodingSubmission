package id.chainlizard.saltiesmovie.viewmodel

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.chainlizard.saltiesmovie.functions.MyObj
import id.chainlizard.saltiesmovie.model.MovieDetailMod
import id.chainlizard.saltiesmovie.functions.MyIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieDetailVM: ViewModel() {
    private var movie: MutableLiveData<MovieDetailMod.MovieDetail>? = null

    fun getMovie(id: Int, context: Context): LiveData<MovieDetailMod.MovieDetail> {
        if(movie == null){
            movie = MutableLiveData()
            loadMovie(id, context)
        }
        return movie as MutableLiveData<MovieDetailMod.MovieDetail>
    }

    fun loadMovie(id: Int, context: Context){
        MyIdlingResource.increment()
        GlobalScope.launch(Dispatchers.Default){
            try{
                val alist = MovieDetailMod.getData(id)
                movie?.postValue(alist)
            }
            catch(e: Exception){
                Handler(Looper.getMainLooper()).post {
                    MyObj.buildMyErrorDialog(context, e.message.toString())
                }
                MyIdlingResource.decrement()
            }
        }
    }
}