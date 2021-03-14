package id.chainlizard.githubsearch.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.chainlizard.githubsearch.Adapter.Detail_List
import id.chainlizard.githubsearch.Adapter.Search_List
import id.chainlizard.githubsearch.Networking
import id.chainlizard.githubsearch.UI.DetailFragment
import kotlinx.coroutines.*
import org.json.JSONObject

class Detail : ViewModel() {
    private var user: MutableLiveData<DetailFragment.User>? = null //menampung keseluruhan detail user
    private var user_detail: MutableLiveData<ArrayList<Detail_List.RowData>>? = null //hanya menampung data yang dibawah nama

    fun getUser(url: String): LiveData<DetailFragment.User>{
        if(user == null){
            user = MutableLiveData<DetailFragment.User>()
            GlobalScope.launch(Dispatchers.Default){
                loadUser(url)
            }
        }
        return user as MutableLiveData<DetailFragment.User>
    }

    fun loadUser(url: String){
        var textJSON: String = ""
        runBlocking {
            val getFromApi = async(context = Dispatchers.IO) { Networking.getJSON(url) }
            textJSON = getFromApi.await()
        }
        val jsonObject = JSONObject(textJSON);
        val ret = DetailFragment.User(
                jsonObject.getString("avatar_url"),
                jsonObject.getString("login"),
                jsonObject.getString("name"),
                jsonObject.getInt("followers"),
                jsonObject.getInt("following"),
                jsonObject.getInt("public_repos"),
                jsonObject.getString("company"),
                jsonObject.getString("blog"),
                jsonObject.getString("location"),
                jsonObject.getString("email"),
                jsonObject.getString("bio"),
                jsonObject.getString("twitter_username")
        )
        user?.postValue(ret)
        loadUsersDetail(ret)
    }

    fun getUsersDetail(): LiveData<ArrayList<Detail_List.RowData>> {
        if (user_detail == null) {
            user_detail = MutableLiveData<ArrayList<Detail_List.RowData>>()
            //initial
            loadUsersDetail(DetailFragment.User("", "", "", 0, 0, 0, "-", null, "-", null, null, null))
        }
        return user_detail as MutableLiveData<ArrayList<Detail_List.RowData>>
    }

    fun loadUsersDetail(user: DetailFragment.User) {
        val userDetail = arrayListOf<Detail_List.RowData>()
        if(!user.bio.isNullOrBlank()){
            userDetail.add(Detail_List.RowData(user.bio!!, Detail_List.JenisField.bio))
        }
        if(!user.company.isNullOrBlank()){
            userDetail.add(Detail_List.RowData(user.company!!, Detail_List.JenisField.company))
        }
        if(!user.blog.isNullOrBlank()){
            userDetail.add(Detail_List.RowData(user.blog!!, Detail_List.JenisField.blog))
        }
        if(!user.location.isNullOrBlank()){
            userDetail.add(Detail_List.RowData(user.location!!, Detail_List.JenisField.location))
        }
        if(!user.email.isNullOrBlank()){
            userDetail.add(Detail_List.RowData(user.email!!, Detail_List.JenisField.email))
        }
        if(!user.twitter.isNullOrBlank()){
            userDetail.add(Detail_List.RowData(user.twitter!!, Detail_List.JenisField.twitter))
        }
        user_detail?.postValue(userDetail)
    }

}