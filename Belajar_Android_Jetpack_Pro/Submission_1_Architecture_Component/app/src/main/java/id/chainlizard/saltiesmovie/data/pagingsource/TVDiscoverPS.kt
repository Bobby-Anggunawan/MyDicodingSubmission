package id.chainlizard.saltiesmovie.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.chainlizard.saltiesmovie.data.MyRepository
import id.chainlizard.saltiesmovie.data.model.TVDetailMod
import id.chainlizard.saltiesmovie.data.model.TVDiscoverMod
import id.chainlizard.saltiesmovie.functions.MyObj
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TVDiscoverPS(private val repository: MyRepository, private val type: MyObj.pageType) :
    PagingSource<Int, TVDiscoverMod.TVPage_List>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TVDiscoverMod.TVPage_List> {
        return try {
            val nextPageNumber = params.key ?: 1
            if (type == MyObj.pageType.Discover) {
                val response =
                    withContext(Dispatchers.Default) { repository.getTVDiscover(nextPageNumber) }
                LoadResult.Page(
                    data = response.results,
                    prevKey = if (nextPageNumber > 1) nextPageNumber - 1 else null,
                    nextKey = if (nextPageNumber < response.total_pages) nextPageNumber + 1 else null
                )
            } else {
                val response =
                    withContext(Dispatchers.Default) { repository.getTVWLAsc(nextPageNumber) }
                LoadResult.Page(
                    data = TVDetailMod.toDisList(response),
                    prevKey = if (nextPageNumber > 1) nextPageNumber - 1 else null,
                    nextKey = if (nextPageNumber < repository.countTVWL()) nextPageNumber + 1 else null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TVDiscoverMod.TVPage_List>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}