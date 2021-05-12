package id.chainlizard.saltiesmovie.functions

import androidx.paging.DataSource
import androidx.room.*
import id.chainlizard.saltiesmovie.data.model.MovieDetailMod
import id.chainlizard.saltiesmovie.data.model.MovieDiscoverMod
import id.chainlizard.saltiesmovie.data.model.TVDiscoverMod

object MyDatabase {
    @Dao
    interface MyDao {
        @Query("SELECT * from MovieFavoriteDetail ORDER BY id ASC limit 20 offset :page-1")
        fun getMovieAsc(page: Int): List<MovieDetailMod.MovieFavoriteDetail>

        @Query("SELECT COUNT(*) FROM MovieFavoriteDetail")
        fun countMovie(): Int

        @Query("SELECT CASE WHEN EXISTS(Select * from MovieFavoriteDetail where id = :movieId) then 1 else 0 end")
        fun movieExists(movieId: Int): Boolean

        @Insert
        fun insertMovie(vararg theMovie: MovieDetailMod.MovieFavoriteDetail)

        @Delete
        fun deleteMovie(theMovie: MovieDetailMod.MovieFavoriteDetail)
    }

    @Database(entities = arrayOf(MovieDetailMod.MovieFavoriteDetail::class), version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun myDB(): MyDao
    }

}