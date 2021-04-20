package id.chainlizard.saltiesmovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.chainlizard.saltiesmovie.functions.MyObj
import id.chainlizard.saltiesmovie.functions.Networking
import id.chainlizard.saltiesmovie.model.MovieDiscoverMod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieDiscoverVM: ViewModel() {
    private var movies: MutableLiveData<ArrayList<MovieDiscoverMod.MoviePage_List>>? = null

    fun getMovie(): LiveData<ArrayList<MovieDiscoverMod.MoviePage_List>> {
        if(movies == null){
            movies = MutableLiveData()
            loadMovies(1)
        }
        return movies as MutableLiveData<ArrayList<MovieDiscoverMod.MoviePage_List>>
    }

    fun loadMovies(page: Int){
        GlobalScope.launch(Dispatchers.Default){
            val alist = MovieDiscoverMod.getData(page)
            movies?.postValue(alist.results)
        }
    }
}