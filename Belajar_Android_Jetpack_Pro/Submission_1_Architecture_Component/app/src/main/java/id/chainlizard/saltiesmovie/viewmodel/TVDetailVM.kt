package id.chainlizard.saltiesmovie.viewmodel

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.chainlizard.saltiesmovie.functions.MyObj
import id.chainlizard.saltiesmovie.model.TVDetailMod
import id.chainlizard.saltiesmovie.functions.MyIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TVDetailVM: ViewModel() {
    private var TV: MutableLiveData<TVDetailMod.TVDetail>? = null

    fun getTV(id: Int, context: Context): LiveData<TVDetailMod.TVDetail> {
        if(TV == null){
            TV = MutableLiveData()
            loadTV(id, context)
        }
        return TV as MutableLiveData<TVDetailMod.TVDetail>
    }

    fun loadTV(id: Int, context: Context){
        MyIdlingResource.increment()
        GlobalScope.launch(Dispatchers.Default){
            try{
                val alist = TVDetailMod.getData(id)
                TV?.postValue(alist)
            }
            catch(e: Exception){
                Handler(Looper.getMainLooper()).post {
                    MyObj.buildMyErrorDialog(context, e.message.toString())
                }
                MyIdlingResource.decrement()
            }
        }
    }
}