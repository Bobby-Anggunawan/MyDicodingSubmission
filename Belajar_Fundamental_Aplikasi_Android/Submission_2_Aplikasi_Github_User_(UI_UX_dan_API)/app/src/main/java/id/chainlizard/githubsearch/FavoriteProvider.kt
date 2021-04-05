package id.chainlizard.githubsearch

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.room.Room

class FavoriteProvider : ContentProvider() {

    companion object {
        private const val PEOPLE = 1
        private const val PEOPLE_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var db: Database.AppDatabase
        init {
            sUriMatcher.addURI(Database.AUTHORITY, Database.TABLE_NAME, PEOPLE)
            sUriMatcher.addURI(Database.AUTHORITY, "${Database.TABLE_NAME}/*", PEOPLE_ID)
        }
    }

    override fun onCreate(): Boolean {
        db = Room.databaseBuilder(
            context as Context,
            Database.AppDatabase::class.java, Database.DATABASE_NAME
        ).build()

        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val objDao = db.userDao()
        val uriMach = sUriMatcher.match(uri)
        Log.e("testQ", uriMach.toString())
        return when (uriMach) {
            PEOPLE -> {
                Log.e("MyErr", "People")
                objDao.getAll()
            }
            PEOPLE_ID -> {
                Log.e("MyErr", "People_Id")
                objDao.getUser(uri.lastPathSegment.toString())
            }
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val objDao = db.userDao()
        val added: Long = when (PEOPLE) {
            sUriMatcher.match(uri) -> {
                val username = values?.getAsString("username") ?: "Null"
                val avatar_url = values?.getAsString("avatar_url")
                val name = values?.getAsString("name")
                val company = values?.getAsString("company")
                val blog = values?.getAsString("blog")
                val location = values?.getAsString("location")
                val email = values?.getAsString("email")
                val bio = values?.getAsString("bio")
                val twitter_username = values?.getAsString("twitter_username")
                val public_repos = values?.getAsInteger("public_repos")
                val followers = values?.getAsInteger("followers")
                val following = values?.getAsInteger("following")
                val usr = Database.User(username, avatar_url, name, company, blog, location, email, bio, twitter_username, public_repos, followers, following)
                objDao.insert(usr)
                1
            }
            else -> 0
        }

        context?.contentResolver?.notifyChange(Database.CONTENT_URI, null)
        return Uri.parse("${Database.CONTENT_URI}/$added")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val objDao = db.userDao()
        val deleted: Int = when (PEOPLE) {
            sUriMatcher.match(uri) ->{
                objDao.deleteById(selection.toString())
                1
            }
            else -> 0
        }
        context?.contentResolver?.notifyChange(Database.CONTENT_URI, null)
        return deleted
    }

    override fun getType(uri: Uri): String? {
        return null
    }
}