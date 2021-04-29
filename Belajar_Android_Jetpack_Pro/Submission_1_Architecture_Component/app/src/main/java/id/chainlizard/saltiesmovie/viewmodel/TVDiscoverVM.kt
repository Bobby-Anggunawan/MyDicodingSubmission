package id.chainlizard.saltiesmovie.viewmodel

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.chainlizard.saltiesmovie.data.MyRepository
import id.chainlizard.saltiesmovie.functions.MyObj
import id.chainlizard.saltiesmovie.data.model.TVDiscoverMod
import id.chainlizard.saltiesmovie.functions.MyIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TVDiscoverVM @Inject constructor(private val repository: MyRepository): ViewModel() {
    private var tv: MutableLiveData<ArrayList<TVDiscoverMod.TVPage_List>>? = null

    fun getTV(context:Context): LiveData<ArrayList<TVDiscoverMod.TVPage_List>> {
        if(tv == null){
            tv = MutableLiveData()
            loadTV(1, context)
        }
        return tv as MutableLiveData<ArrayList<TVDiscoverMod.TVPage_List>>
    }

    fun loadTV(page: Int, context:Context){
        MyIdlingResource.increment()
        GlobalScope.launch(Dispatchers.Default){
            try{
                val alist = repository.getTVDiscover(page)
                tv?.postValue(alist.results)
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