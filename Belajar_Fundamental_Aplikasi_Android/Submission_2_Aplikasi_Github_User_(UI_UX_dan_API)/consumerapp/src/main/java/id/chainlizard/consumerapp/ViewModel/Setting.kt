package id.chainlizard.consumerapp.ViewModel

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.chainlizard.consumerapp.TypeList
import id.chainlizard.consumerapp.UI.MyTimePickerDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Setting: ViewModel() {
    private var waktu: MutableLiveData<String>? = null

    fun getTime(activity: Activity): LiveData<String> {
        if (waktu == null) {
            waktu = MutableLiveData()
            GlobalScope.launch(Dispatchers.Default){
                setTime(activity)
            }
        }
        return waktu as MutableLiveData<String>
    }

    fun setTime(activity: Activity){
        val jam = TypeList.readSharedPreference(activity, MyTimePickerDialog.KEY_HOUR, 9)
        val menit = TypeList.readSharedPreference(activity, MyTimePickerDialog.KEY_MINUTE, 0)
        var jamStr = ""
        var menitStr = ""
        if(jam<10){
            jamStr = "0$jam"
        }
        else{
            jamStr = jam.toString()
        }
        if(menit<10){
            menitStr = "0$menit"
        }
        else{
            menitStr = menit.toString()
        }
        val txt = "$jamStr:$menitStr"

        waktu?.postValue(txt)
    }
}