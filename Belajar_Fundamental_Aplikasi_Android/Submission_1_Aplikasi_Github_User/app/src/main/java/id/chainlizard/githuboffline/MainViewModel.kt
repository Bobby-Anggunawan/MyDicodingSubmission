package id.chainlizard.githuboffline

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val switchOn = MutableLiveData<Boolean>()

    fun setUser(swState: Boolean){
        switchOn.postValue(swState)
    }

    fun getUser(): MutableLiveData<Boolean>{
        return switchOn
    }
}
