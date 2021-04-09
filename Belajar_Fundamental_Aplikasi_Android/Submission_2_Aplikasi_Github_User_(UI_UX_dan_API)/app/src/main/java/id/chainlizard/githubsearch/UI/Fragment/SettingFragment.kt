package id.chainlizard.githubsearch.UI.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import com.suke.widget.SwitchButton
import id.chainlizard.githubsearch.R
import id.chainlizard.githubsearch.TypeList
import id.chainlizard.githubsearch.UI.MyTimePickerDialog
import id.chainlizard.githubsearch.ViewModel.Setting
import java.util.*


class SettingFragment : Fragment() {

    lateinit var dialogTimePicker: DialogFragment
    lateinit var openDialogButton: Button
    lateinit var mySwitchButton: SwitchButton
    companion object{
        lateinit var model: Setting
        const val KEY_SWITCH_STATE = "isAlarmOn"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_setting, container, false)
        openDialogButton = root.findViewById<Button>(R.id.setTime)
        openDialogButton.setOnClickListener {
            dialogTimePicker = MyTimePickerDialog()
            dialogTimePicker.show(requireActivity().supportFragmentManager, "My Time Picker")
        }

        root.findViewById<MaterialToolbar>(R.id.topAppBar).setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        val mySwitchState = TypeList.readSharedPreference(requireActivity(), KEY_SWITCH_STATE, false)
        mySwitchButton = root.findViewById(R.id.switch_button)
        mySwitchButton.isChecked = mySwitchState
        mySwitchButton.setOnCheckedChangeListener { view, isChecked ->
            if(isChecked){
                val jam = TypeList.readSharedPreference(requireActivity(), MyTimePickerDialog.KEY_HOUR, 9)
                val menit = TypeList.readSharedPreference(requireActivity(), MyTimePickerDialog.KEY_MINUTE, 0)
                val kalender = Calendar.getInstance()
                kalender.set(Calendar.HOUR_OF_DAY, jam)
                kalender.set(Calendar.MINUTE, menit)
                kalender.set(Calendar.SECOND, 0)

                MyTimePickerDialog.startAlarm(kalender, requireContext())
            }
            else{
                MyTimePickerDialog.cancelAlarm(requireContext())
            }
            TypeList.writeSharedPreference(requireActivity(), KEY_SWITCH_STATE, isChecked)
        }

        //view model
        model = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            Setting::class.java
        )
        model.getTime(requireActivity()).observe(requireActivity(), {
            openDialogButton.text = it
        })

        return root
    }
}