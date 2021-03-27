package id.chainlizard.githubsearch.ViewModel

import android.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import id.chainlizard.githubsearch.Adapter.Search_List
import id.chainlizard.githubsearch.Networking
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject

class Search : ViewModel() {
    private var users: MutableLiveData<ArrayList<Search_List.RowData>>? = null
    enum class jsonType{
        search, follow
    }

    fun getUsers(jenis: jsonType, url: String = "https://api.github.com/users"): LiveData<ArrayList<Search_List.RowData>> {
        if (users == null) {
            users = MutableLiveData<ArrayList<Search_List.RowData>>()
            GlobalScope.launch(Dispatchers.Default){
                loadUsers(jenis, url)
            }
        }
        return users as MutableLiveData<ArrayList<Search_List.RowData>>
    }


    fun loadUsers(jenis: jsonType, url: String) {
        var textJSON: String = ""
        runBlocking {
            val getFromApi = async(context = Dispatchers.IO) { Networking.getJSON(url) }
            textJSON = getFromApi.await()
        }
        val kembalian = arrayListOf<Search_List.RowData>()
        if(jenis == jsonType.search){
            val jsonObject = JSONObject(textJSON);
            val items = jsonObject.getJSONArray("items")
            for(x in 0..items.length()-1){
                val aClass = Search_List.RowData(items.getJSONObject(x).getString("avatar_url"), items.getJSONObject(x).getString("login"))
                kembalian.add(aClass)
            }
        }
        else if(jenis == jsonType.follow){
            val jsonArr = JSONArray(textJSON)
            for(x in 0..jsonArr.length()-1){
                kembalian.add(Search_List.RowData(jsonArr.getJSONObject(x).getString("avatar_url"), jsonArr.getJSONObject(x).getString("login")))
            }
        }
        users?.postValue(kembalian)

    }
}