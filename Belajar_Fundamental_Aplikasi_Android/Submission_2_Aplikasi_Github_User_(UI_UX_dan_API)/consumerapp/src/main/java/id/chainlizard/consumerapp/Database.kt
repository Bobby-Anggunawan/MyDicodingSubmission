package id.chainlizard.consumerapp

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object Database {

    const val AUTHORITY = "id.chainlizard.githubsearch.db"
    const val SCHEME = "content"
    const val TABLE_NAME = "user"

    val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
        .authority(AUTHORITY)
        .appendPath(TABLE_NAME)
        .build()


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