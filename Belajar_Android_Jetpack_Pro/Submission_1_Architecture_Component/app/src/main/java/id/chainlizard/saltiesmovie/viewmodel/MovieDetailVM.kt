package id.chainlizard.saltiesmovie.viewmodel

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.chainlizard.saltiesmovie.functions.MyObj
import id.chainlizard.saltiesmovie.data.model.MovieDetailMod
import id.chainlizard.saltiesmovie.functions.MyIdlingResource
import id.chainlizard.saltiesmovie.data.MyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailVM @Inject constructor(private val repository: MyRepository): ViewModel() {
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
                val alist = repository.getMovieDetail(id)
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