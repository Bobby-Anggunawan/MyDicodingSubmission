package id.chainlizard.saltiesmovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.chainlizard.saltiesmovie.functions.MyObj
import id.chainlizard.saltiesmovie.functions.Networking
import id.chainlizard.saltiesmovie.model.TVDetailMod
import id.chainlizard.saltiesmovie.myIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class TVDetailVM: ViewModel() {
    private var TV: MutableLiveData<TVDetailMod.TVDetail>? = null

    fun getTV(id: Int): LiveData<TVDetailMod.TVDetail> {
        if(TV == null){
            TV = MutableLiveData()
            loadTV(id)
        }
        return TV as MutableLiveData<TVDetailMod.TVDetail>
    }

    fun loadTV(id: Int){
        myIdlingResource.increment()
        GlobalScope.launch(Dispatchers.Default){
            try{
                val alist = TVDetailMod.getData(id)
                TV?.postValue(alist)
            }
            catch(e: Exception){
                myIdlingResource.decrement()
            }
        }
    }
}