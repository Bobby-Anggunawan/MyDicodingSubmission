package id.chainlizard.consumerapp.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.chainlizard.consumerapp.Database
import id.chainlizard.consumerapp.TypeList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Favorite: ViewModel() {
    private var users: MutableLiveData<ArrayList<TypeList.User>>? = null

    fun getUsers(context: Context): LiveData<ArrayList<TypeList.User>> {
        if (users == null) {
            users = MutableLiveData()
            GlobalScope.launch(Dispatchers.Default){
                loadUsers(context)
            }
        }
        return users as MutableLiveData<ArrayList<TypeList.User>>
    }

    fun loadUsers(context: Context){
        val alist = Database.DatabaseHelper.getAllUser(context)
        users?.postValue(alist)
    }
}