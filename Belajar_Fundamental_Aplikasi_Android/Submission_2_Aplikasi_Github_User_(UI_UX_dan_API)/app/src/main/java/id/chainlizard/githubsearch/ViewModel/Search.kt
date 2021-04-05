package id.chainlizard.githubsearch.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.chainlizard.githubsearch.MainActivity
import id.chainlizard.githubsearch.Networking
import id.chainlizard.githubsearch.TypeList
import kotlinx.coroutines.*
import java.lang.reflect.Type


class Search : ViewModel() {
    private var users: MutableLiveData<ArrayList<TypeList.User>>? = null
    enum class jsonType{
        search, follow
    }

    fun getUsers(jenis: jsonType, url: String = "https://api.github.com/users"): LiveData<ArrayList<TypeList.User>> {
        if (users == null) {
            users = MutableLiveData()
            GlobalScope.launch(Dispatchers.Default){
                loadUsers(jenis, url)
            }
        }
        return users as MutableLiveData<ArrayList<TypeList.User>>
    }


    fun loadUsers(jenis: jsonType, url: String) {
        try{
            var textJSON = ""
            runBlocking {
                val getFromApi = async(context = Dispatchers.IO) { Networking.getJSON(url) }
                textJSON = getFromApi.await()
            }
            val kembalian = arrayListOf<TypeList.User>()
            val gson = Gson()
            if(jenis == jsonType.search){
                val obj = gson.fromJson(textJSON, TypeList.SearchResult::class.java)
                kembalian.addAll(obj.items)
            }
            else if(jenis == jsonType.follow){
                val collectionType = object :
                    TypeToken<Collection<TypeList.User?>?>() {}.type as Type
                val obj = gson.fromJson(textJSON, collectionType) as List<TypeList.User>
                kembalian.addAll(obj)
            }
            users?.postValue(kembalian)
        }

        catch(e: Exception){
            Snackbar.make(MainActivity.mainLayout, e.message.toString(), Snackbar.LENGTH_LONG).show()
        }

    }
}