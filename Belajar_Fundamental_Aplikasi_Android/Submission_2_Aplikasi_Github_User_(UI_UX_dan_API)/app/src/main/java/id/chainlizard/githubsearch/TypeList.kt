package id.chainlizard.githubsearch

import android.app.Activity
import android.content.Context
import android.database.Cursor

object TypeList {
    data class User(
        var login: String? = null,
        var id: Int? = null,
        var node_id: String? = null,
        var avatar_url: String? = null,
        var gravatar_id: String? = null,
        var url: String? = null,
        var html_url: String? = null,
        var followers_url: String? = null,
        var following_url: String? = null,
        var gists_url: String? = null,
        var starred_url: String? = null,
        var subscriptions_url: String? = null,
        var organizations_url: String? = null,
        var repos_url: String? = null,
        var events_url: String? = null,
        var received_events_url: String? = null,
        var type: String? = null,
        var site_admin: Boolean? = null,
        var name: String? = null,
        var company: String? = null,
        var blog: String? = null,
        var location: String? = null,
        var email: String? = null,
        var hireable: Boolean? = null,
        var bio: String? = null,
        var twitter_username: String? = null,
        var public_repos: Int? = null,
        var public_gists: Int? = null,
        var followers: Int? = null,
        var following: Int? = null,
        var created_at: String? = null,
        var updated_at: String? = null
    )
    data class SearchResult(
        var total_count: Int = 0,
        var incomplete_results: Boolean = false,
        var items: List<User> = listOf()
    )
    data class MyWidgetItem(
        var username: String,
        var avatar: String
    )

    fun mapCursorToUser(data: Cursor?): ArrayList<User>{
        val ret = arrayListOf<User>()
        data?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow("username"))
                val avatar = getString(getColumnIndexOrThrow("avatar_url"))
                val name = getString(getColumnIndexOrThrow("name"))
                val company = getString(getColumnIndexOrThrow("company"))
                val blog = getString(getColumnIndexOrThrow("blog"))
                val location = getString(getColumnIndexOrThrow("location"))
                val email = getString(getColumnIndexOrThrow("email"))
                val bio = getString(getColumnIndexOrThrow("bio"))
                val twitter_username = getString(getColumnIndexOrThrow("twitter_username"))
                val public_repos = getInt(getColumnIndexOrThrow("public_repos"))
                val followers = getInt(getColumnIndexOrThrow("followers"))
                val following = getInt(getColumnIndexOrThrow("following"))


                val aUser = TypeList.User()
                aUser.login = username
                aUser.avatar_url = avatar
                aUser.name = name
                aUser.company = company
                aUser.blog = blog
                aUser.location = location
                aUser.email = email
                aUser.bio = bio
                aUser.twitter_username = twitter_username
                aUser.public_repos = public_repos
                aUser.followers = followers
                aUser.following = following

                ret.add(aUser)
            }
        }
        return ret
    }

    fun writeSharedPreference(activity: Activity, key: String, value: String){
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        with (sharedPref!!.edit()) {
            putString(key, value)
            apply()
        }
    }
    fun writeSharedPreference(activity: Activity, key: String, value: Int){
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        with (sharedPref!!.edit()) {
            putInt(key, value)
            apply()
        }
    }
    fun writeSharedPreference(activity: Activity, key: String, value: Boolean){
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        with (sharedPref!!.edit()) {
            putBoolean(key, value)
            apply()
        }
    }

    fun readSharedPreference(activity: Activity, key: String, defaultValue: String): String{
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getString(key, defaultValue).toString()
    }
    fun readSharedPreference(activity: Activity, key: String, defaultValue: Int): Int{
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getInt(key, defaultValue)
    }
    fun readSharedPreference(activity: Activity, key: String, defaultValue: Boolean): Boolean{
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getBoolean(key, defaultValue)
    }
}