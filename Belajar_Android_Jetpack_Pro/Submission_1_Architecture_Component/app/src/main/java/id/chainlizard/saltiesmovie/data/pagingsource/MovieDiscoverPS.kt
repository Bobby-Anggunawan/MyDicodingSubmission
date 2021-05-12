package id.chainlizard.saltiesmovie.data.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.test.espresso.IdlingResource
import dagger.hilt.android.AndroidEntryPoint
import id.chainlizard.saltiesmovie.BuildConfig
import id.chainlizard.saltiesmovie.data.MyRepository
import id.chainlizard.saltiesmovie.data.model.MovieDetailMod
import id.chainlizard.saltiesmovie.data.model.MovieDiscoverMod
import id.chainlizard.saltiesmovie.functions.MyObj
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.ceil

class MovieDiscoverPS(private var repository: MyRepository, private var type: MyObj.pageType): PagingSource<Int, MovieDiscoverMod.MoviePage_List>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDiscoverMod.MoviePage_List> {
        return try {
            val nextPageNumber = params.key ?: 1
            if(type == MyObj.pageType.Discover){
                val response = withContext(Dispatchers.Default) { repository.getMovieDiscover(nextPageNumber)}
                LoadResult.Page(
                    data = response.results,
                    prevKey = if (nextPageNumber > 1) nextPageNumber - 1 else null,
                    nextKey = if (nextPageNumber < response.total_pages) nextPageNumber + 1 else null
                )
            }
            else{
                val response = withContext(Dispatchers.Default) { repository.getMovieWLAsc(nextPageNumber)}
                LoadResult.Page(
                    data = MovieDetailMod.convertToListDis(response),
                    prevKey = if (nextPageNumber > 1) nextPageNumber - 1 else null,
                    nextKey = if (nextPageNumber < repository.countMovieWL()) nextPageNumber + 1 else null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieDiscoverMod.MoviePage_List>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}