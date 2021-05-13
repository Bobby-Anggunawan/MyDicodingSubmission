package id.chainlizard.saltiesmovie.functions

import androidx.room.*
import id.chainlizard.saltiesmovie.data.model.MovieDetailMod
import id.chainlizard.saltiesmovie.data.model.TVDetailMod

object MyDatabase {
    @Dao
    interface MyDao {
        @Query("SELECT * from MovieFavoriteDetail limit 1 offset :page-1")
        fun getMovieAsc(page: Int): List<MovieDetailMod.MovieFavoriteDetail>

        @Query("SELECT * from TVFavoriteDetail limit 1 offset :page-1")
        fun getTVAsc(page: Int): List<TVDetailMod.TVFavoriteDetail>

        @Query("SELECT COUNT(*) FROM MovieFavoriteDetail")
        fun countMovie(): Int

        @Query("SELECT COUNT(*) FROM TVFavoriteDetail")
        fun countTV(): Int

        @Query("SELECT CASE WHEN EXISTS(Select * from MovieFavoriteDetail where id = :movieId) then 1 else 0 end")
        fun movieExists(movieId: Int): Boolean

        @Query("SELECT CASE WHEN EXISTS(Select * from TVFavoriteDetail where id = :tvId) then 1 else 0 end")
        fun tvExists(tvId: Int): Boolean

        @Insert
        fun insertMovie(vararg theMovie: MovieDetailMod.MovieFavoriteDetail)

        @Insert
        fun insertTV(vararg theTV: TVDetailMod.TVFavoriteDetail)

        @Delete
        fun deleteMovie(theMovie: MovieDetailMod.MovieFavoriteDetail)

        @Delete
        fun deleteTV(theTV: TVDetailMod.TVFavoriteDetail)
    }

    @Database(
        entities = arrayOf(
            MovieDetailMod.MovieFavoriteDetail::class,
            TVDetailMod.TVFavoriteDetail::class
        ), version = 1
    )
    abstract class AppDatabase : RoomDatabase() {
        abstract fun myDB(): MyDao
    }

}