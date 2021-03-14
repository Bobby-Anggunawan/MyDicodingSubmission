package id.chainlizard.githubsearch

import android.util.Log
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.SyncHttpClient
import cz.msebera.android.httpclient.Header

object Networking {
    fun getJSON(url: String): String{
        var result: String = ""
        val client = SyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token 127f5afd0fdc03cb48b0696a5f451d5f1eddd162")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                result = String(responseBody)
                Log.e("My Test", result)
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                result = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Log.e("My Test", result)
            }
        })
        return result
    }
}