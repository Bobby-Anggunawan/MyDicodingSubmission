package id.chainlizard.githubsearch

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import androidx.room.*
import androidx.room.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

object Database {

    const val AUTHORITY = "id.chainlizard.githubsearch.db"
    const val SCHEME = "content"
    const val TABLE_NAME = "user"
    const val DATABASE_NAME = "myAppDB"

    val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
        .authority(AUTHORITY)
        .appendPath(TABLE_NAME)
        .build()

    @Entity(tableName = "user")
    data class User(
            @PrimaryKey val username: String,
            @ColumnInfo(name = "avatar_url") val avatarUrl: String?,
            @ColumnInfo(name = "name") val fullName: String?,
            @ColumnInfo(name = "company") val company: String?,
            @ColumnInfo(name = "blog") val blog: String?,
            @ColumnInfo(name = "location") val location: String?,
            @ColumnInfo(name = "email") val email: String?,
            @ColumnInfo(name = "bio") val bio: String?,
            @ColumnInfo(name = "twitter_username") val twitterUsername: String?,
            @ColumnInfo(name = "public_repos") val repo: Int?,
            @ColumnInfo(name = "followers") val followers: Int?,
            @ColumnInfo(name = "following") val following: Int?,
    )

    @Entity(tableName = "widgetdata")
    data class WidgetData(
            @PrimaryKey val username: String,
            @ColumnInfo(name = "avatar", typeAffinity = ColumnInfo.BLOB) val avatar: ByteArray?
    )

    @Dao
    interface UserDao {
        @Query("SELECT * FROM user")
        fun getAll(): Cursor

        @Query("SELECT * FROM user WHERE username = :login")
        fun getUser(login: String): Cursor

        @Insert
        fun insert(user: User)

        @Query("DELETE FROM user WHERE username = :id")
        fun deleteById(id: String)
    }

    @Dao
    interface WidgetDataDao{
        @Query("SELECT * FROM widgetdata")
        fun getAll(): ArrayList<WidgetData>

        @Insert
        fun insert(user: WidgetData)

        @Query("DELETE FROM widgetdata WHERE username = :id")
        fun deleteById(id: String)
    }

    @Database(entities = arrayOf(User::class), version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun userDao(): UserDao
    }

    object DatabaseHelper{
        fun getAllUser(context: Context): ArrayList<TypeList.User>{
            val data = context.contentResolver.query(CONTENT_URI, null, null, null, null)
            return TypeList.mapCursorToUser(data)
        }

        fun getUserById(context: Context, username: String): TypeList.User{
            try{
                val myUri = Uri.Builder().scheme(SCHEME)
                        .authority(AUTHORITY)
                        .appendPath(TABLE_NAME)
                        .appendPath(username)
                        .build()
                Log.e("MyErr", myUri.path.toString())
                val data = context.contentResolver.query(myUri, null, null, null, null)
                return  TypeList.mapCursorToUser(data)[0]
            }
            catch(e: Exception){
                return TypeList.User()
            }
        }

        fun insertUser(context: Context, user: TypeList.User){
            val values = ContentValues()
            values.put("username", user.login ?: "null")
            values.put("avatar_url", user.avatar_url)
            values.put("name", user.name)
            values.put("company", user.company)
            values.put("blog", user.blog)
            values.put("location", user.location)
            values.put("email", user.email)
            values.put("bio", user.bio)
            values.put("twitter_username", user.twitter_username)
            values.put("public_repos", user.public_repos)
            values.put("followers", user.followers)
            values.put("following", user.following)

            GlobalScope.launch(Dispatchers.Default){
                try{
                    context.contentResolver.insert(CONTENT_URI, values)
                }
                catch (e: Exception){}
            }

        }

        fun deleteUser(context: Context, id: String){
            context.contentResolver.delete(CONTENT_URI, id, null)
        }
    }
}