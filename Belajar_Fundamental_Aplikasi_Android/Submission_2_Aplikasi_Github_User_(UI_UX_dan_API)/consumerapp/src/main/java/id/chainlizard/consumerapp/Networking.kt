package id.chainlizard.consumerapp

import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.SyncHttpClient
import cz.msebera.android.httpclient.Header


object Networking {
    fun getJSON(url: String): String{
        var result = ""
        val client = SyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token ${BuildConfig.githubApiToken}")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                result = String(responseBody)
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                result = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
            }
        })
        return result
    }
}