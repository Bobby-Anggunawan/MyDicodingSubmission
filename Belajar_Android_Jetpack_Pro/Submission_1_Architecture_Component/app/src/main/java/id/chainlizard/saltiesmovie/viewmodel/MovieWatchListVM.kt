package id.chainlizard.saltiesmovie.viewmodel

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import id.chainlizard.saltiesmovie.data.MyRepository
import id.chainlizard.saltiesmovie.data.model.MovieDiscoverMod
import javax.inject.Inject

@HiltViewModel
class MovieWatchListVM @Inject constructor(private val repository: MyRepository): ViewModel() {
    val pagingItems =
        Pager(config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { repository.movieWatchListPS }
        ).liveData.cachedIn(viewModelScope)

}