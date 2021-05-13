package id.chainlizard.saltiesmovie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.chainlizard.saltiesmovie.data.MyRepository
import javax.inject.Inject

@HiltViewModel
class TVDiscoverVM @Inject constructor(private val repository: MyRepository) : ViewModel() {
    val pagingItems =
        Pager(config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { repository.tvPS }
        ).liveData.cachedIn(viewModelScope)
}