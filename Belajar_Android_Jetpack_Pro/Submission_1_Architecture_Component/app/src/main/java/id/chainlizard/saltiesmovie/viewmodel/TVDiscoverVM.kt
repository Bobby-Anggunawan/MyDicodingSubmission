package id.chainlizard.saltiesmovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.chainlizard.saltiesmovie.functions.MyObj
import id.chainlizard.saltiesmovie.functions.Networking
import id.chainlizard.saltiesmovie.model.TVDiscoverMod
import id.chainlizard.saltiesmovie.myIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TVDiscoverVM: ViewModel() {
    private var tv: MutableLiveData<ArrayList<TVDiscoverMod.TVPage_List>>? = null

    fun getTV(): LiveData<ArrayList<TVDiscoverMod.TVPage_List>> {
        if(tv == null){
            tv = MutableLiveData()
            loadTV(1)
        }
        return tv as MutableLiveData<ArrayList<TVDiscoverMod.TVPage_List>>
    }

    fun loadTV(page: Int){
        myIdlingResource.increment()
        GlobalScope.launch(Dispatchers.Default){
            try{
                val alist = TVDiscoverMod.getData(page)
                tv?.postValue(alist.results)
            }
            catch(e: Exception){
                myIdlingResource.decrement()
            }
        }
    }
}