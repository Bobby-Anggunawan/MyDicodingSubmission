package id.chainlizard.saltiesmovie.viewmodel

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.chainlizard.saltiesmovie.data.MyRepository
import id.chainlizard.saltiesmovie.data.model.MovieDetailMod
import id.chainlizard.saltiesmovie.data.model.MovieDiscoverMod
import id.chainlizard.saltiesmovie.functions.MyIdlingResource
import id.chainlizard.saltiesmovie.functions.MyObj
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailVM @Inject constructor(private val repository: MyRepository) : ViewModel() {
    private var movie: MutableLiveData<MovieDetailMod.MovieDetail>? = null
    private var favorit: MutableLiveData<Boolean>? = null

    fun initFavorit(id: Int){
        if(repository.movieExist(id)){
            favorit?.postValue(true)
        }
        else{
            favorit?.postValue(false)
        }
    }

    fun getFavorit(id: Int): LiveData<Boolean>{
        if (favorit == null) {
            favorit = MutableLiveData()
            initFavorit(id)
        }
        return favorit as MutableLiveData<Boolean>
    }

    fun postFavorit(id: Int){

        val movie = repository.getMovieDetail(id)
        val conMovie = MovieDetailMod.convertToFav(movie)
        if(repository.movieExist(movie.id)){
            repository.removeMovie(conMovie)
            favorit?.postValue(false)
        }
        else{
            repository.addMovie(conMovie)
            favorit?.postValue(true)
        }
    }

    fun getMovie(
        id: Int,
        context: Context? = null,
        mySpin: ProgressBar? = null
    ): LiveData<MovieDetailMod.MovieDetail> {
        if (movie == null) {
            movie = MutableLiveData()
            loadMovie(id, context, mySpin)
        }
        return movie as MutableLiveData<MovieDetailMod.MovieDetail>
    }

    fun loadMovie(id: Int, context: Context? = null, mySpin: ProgressBar? = null) {
        MyIdlingResource.increment()
        GlobalScope.launch(Dispatchers.Default) {
            try {
                val alist = repository.getMovieDetail(id)
                movie?.postValue(alist)
            } catch (e: Exception) {
                if (context !== null) {
                    Handler(Looper.getMainLooper()).post {
                        MyObj.buildMyErrorDialog(context, e.message.toString())
                        mySpin!!.visibility = View.GONE
                    }
                }
                MyIdlingResource.decrement()
            }
        }
    }
}