package id.chainlizard.saltiesmovie.viewmodel

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.chainlizard.saltiesmovie.data.MyRepository
import id.chainlizard.saltiesmovie.data.model.TVDetailMod
import id.chainlizard.saltiesmovie.functions.MyIdlingResource
import id.chainlizard.saltiesmovie.functions.MyObj
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TVDetailVM @Inject constructor(private val repository: MyRepository) : ViewModel() {
    private var TV: MutableLiveData<TVDetailMod.TVDetail>? = null

    fun getTV(
        id: Int,
        context: Context? = null,
        mySpin: ProgressBar? = null
    ): LiveData<TVDetailMod.TVDetail> {
        if (TV == null) {
            TV = MutableLiveData()
            loadTV(id, context, mySpin)
        }
        return TV as MutableLiveData<TVDetailMod.TVDetail>
    }

    fun loadTV(id: Int, context: Context?, mySpin: ProgressBar?) {
        MyIdlingResource.increment()
        GlobalScope.launch(Dispatchers.Default) {
            try {
                val alist = repository.getTVDetail(id)
                TV?.postValue(alist)
            } catch (e: Exception) {
                if (context != null) {
                    Handler(Looper.getMainLooper()).post {
                        MyObj.buildMyErrorDialog(context, e.message.toString())
                        mySpin!!.visibility = View.GONE
                    }
                }
                MyIdlingResource.decrement()
            }
        }
    }
}