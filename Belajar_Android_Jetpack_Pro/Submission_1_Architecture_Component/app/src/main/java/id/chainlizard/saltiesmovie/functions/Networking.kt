package id.chainlizard.saltiesmovie.functions

import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.SyncHttpClient
import cz.msebera.android.httpclient.Header

object Networking {
    class NetworkBuilder{
        private data class headerCon(var name: String, var value: String)

        private var myBaseUrl = ""
        private var queryParamString = "?"
        private val headerList = arrayListOf<headerCon>()

        fun baseUrl(url: String): NetworkBuilder{
            myBaseUrl = url
            return this
        }
        fun addHeader(name: String, value: String): NetworkBuilder{
            headerList.add(headerCon(name, value))
            return this
        }
        fun addQueryParamater(name: String, value: String): NetworkBuilder{
            if(queryParamString == "?"){
                queryParamString += "$name=$value"
            }
            else{
                queryParamString += "&$name=$value"
            }
            return this
        }
        fun runSync(): String{
            var result = ""
            val url = myBaseUrl+queryParamString
            val client = SyncHttpClient()
            headerList.forEach {
                client.addHeader(it.name, it.value)
            }
            client.get(url, object : AsyncHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                    result = String(responseBody)
                }

                override fun onFailure(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray?, error: Throwable?) {
                    result = when (statusCode) {
                        401 -> "$statusCode : Bad Request"
                        403 -> "$statusCode : Forbidden"
                        404 -> "$statusCode : Not Found"
                        else -> "$statusCode : ${error?.message}"
                    }
                }
            })
            return result
        }
    }
}