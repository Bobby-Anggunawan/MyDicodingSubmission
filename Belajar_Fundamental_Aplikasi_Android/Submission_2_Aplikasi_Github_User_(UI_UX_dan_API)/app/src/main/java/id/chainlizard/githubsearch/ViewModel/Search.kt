package id.chainlizard.githubsearch.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.chainlizard.githubsearch.Adapter.Search_List
import id.chainlizard.githubsearch.Networking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.json.JSONObject

class Search : ViewModel() {
    private var users: MutableLiveData<ArrayList<Search_List.RowData>>? = null

    fun getUsers(): LiveData<ArrayList<Search_List.RowData>> {
        if (users == null) {
            users = MutableLiveData<ArrayList<Search_List.RowData>>()
            loadUsers("https://api.github.com/search/users?q=qiyi")
        }
        return users as MutableLiveData<ArrayList<Search_List.RowData>>
    }

    fun loadUsers(url: String) {
        var textJSON: String = ""
        runBlocking {
            val getFromApi = async(context = Dispatchers.IO) { Networking.getJSON(url) }
            textJSON = getFromApi.await()
        }
        val jsonObject = JSONObject(textJSON);
        val items = jsonObject.getJSONArray("items")
        val kembalian = arrayListOf<Search_List.RowData>()
        for(x in 0..items.length()-1){
            val aClass = Search_List.RowData(items.getJSONObject(x).getString("avatar_url"), items.getJSONObject(x).getString("login"))
            kembalian.add(aClass)
        }
        users?.postValue(kembalian)
    }
}