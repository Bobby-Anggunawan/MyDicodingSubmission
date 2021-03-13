package id.chainlizard.githuboffline

import androidx.room.*
import androidx.room.Database

object Database {
    @Entity
    data class User(
        @PrimaryKey val uid: String
    )

    @Dao
    interface UserDao {
        @Query("SELECT * FROM user")
        fun getAll(): List<User>

        @Insert
        fun insertAll(vararg users: User)

        @Delete
        fun delete(user: User)
    }

    @Database(entities = arrayOf(User::class), version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun userDao(): UserDao
    }


}