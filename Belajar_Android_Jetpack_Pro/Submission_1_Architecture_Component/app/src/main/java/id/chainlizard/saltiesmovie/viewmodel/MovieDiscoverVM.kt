package id.chainlizard.saltiesmovie.viewmodel

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import id.chainlizard.saltiesmovie.data.MyRepository
import id.chainlizard.saltiesmovie.data.model.MovieDiscoverMod
import id.chainlizard.saltiesmovie.data.pagingsource.MovieDiscoverPS
import id.chainlizard.saltiesmovie.functions.MyIdlingResource
import id.chainlizard.saltiesmovie.functions.MyObj
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDiscoverVM @Inject constructor(private val repository: MyRepository) : ViewModel() {
    val pagingItems =
        Pager(config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { repository.moviePS }
        ).liveData.cachedIn(viewModelScope)
}