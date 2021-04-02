package id.chainlizard.githubsearch.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import id.chainlizard.githubsearch.Adapter.Detail_List
import id.chainlizard.githubsearch.MainActivity
import id.chainlizard.githubsearch.Networking
import id.chainlizard.githubsearch.TypeList
import id.chainlizard.githubsearch.UI.DetailFragment
import kotlinx.coroutines.*
import org.json.JSONObject

class Detail : ViewModel() {
    data class Info(
        var usr: TypeList.User,                     //info user keseluruhan
        var detail: ArrayList<Detail_List.RowData>  //hanya menampung detail user(list dibawah nama)
    )
    private var user: MutableLiveData<Info>? = null //menampung keseluruhan detail user

    fun getUser(url: String): LiveData<Info>{
        if(user == null){
            user = MutableLiveData()
            GlobalScope.launch(Dispatchers.Default){
                loadUser(url)
            }
        }
        return user as MutableLiveData<Info>
    }

    fun loadUser(url: String){
        try{
            var textJSON: String = ""
            runBlocking {
                val getFromApi = async(context = Dispatchers.IO) { Networking.getJSON(url) }
                textJSON = getFromApi.await()
            }
            val gson = Gson()

            val obj = gson.fromJson(textJSON, TypeList.User::class.java) as TypeList.User
            //load detail list
            val userDetail = arrayListOf<Detail_List.RowData>()
            if(!obj.company.isNullOrEmpty()){
                userDetail.add(Detail_List.RowData(obj.company!!, Detail_List.JenisField.company))
            }
            if(!obj.blog.isNullOrEmpty()){
                userDetail.add(Detail_List.RowData(obj.blog!!, Detail_List.JenisField.blog))
            }
            if(!obj.location.isNullOrEmpty()){
                userDetail.add(Detail_List.RowData(obj.location!!, Detail_List.JenisField.location))
            }
            if(!obj.email.isNullOrEmpty()){
                userDetail.add(Detail_List.RowData(obj.email!!, Detail_List.JenisField.email))
            }
            if(!obj.twitter_username.isNullOrEmpty()){
                userDetail.add(Detail_List.RowData(obj.twitter_username!!, Detail_List.JenisField.twitter))
            }

            user?.postValue(Info(obj, userDetail))
        }
        catch (e: Exception){
            Snackbar.make(MainActivity.mainLayout, e.message.toString(), Snackbar.LENGTH_LONG).show()
        }
    }
}