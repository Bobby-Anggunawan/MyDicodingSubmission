package id.chainlizard.saltiesmovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.chainlizard.saltiesmovie.functions.MyObj
import id.chainlizard.saltiesmovie.functions.Networking
import id.chainlizard.saltiesmovie.model.MovieDetailMod
import id.chainlizard.saltiesmovie.myIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieDetailVM: ViewModel() {
    private var movie: MutableLiveData<MovieDetailMod.MovieDetail>? = null

    fun getMovie(id: Int): LiveData<MovieDetailMod.MovieDetail> {
        if(movie == null){
            movie = MutableLiveData()
            loadMovie(id)
        }
        return movie as MutableLiveData<MovieDetailMod.MovieDetail>
    }

    fun loadMovie(id: Int){
        myIdlingResource.increment()
        GlobalScope.launch(Dispatchers.Default){
            try{
                val alist = MovieDetailMod.getData(id)
                movie?.postValue(alist)
            }
            catch(e: Exception){
                myIdlingResource.decrement()
            }
        }
    }
}