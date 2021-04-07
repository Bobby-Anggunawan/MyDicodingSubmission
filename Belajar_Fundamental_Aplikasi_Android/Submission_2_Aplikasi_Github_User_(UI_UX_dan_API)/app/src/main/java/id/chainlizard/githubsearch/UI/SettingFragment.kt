package id.chainlizard.githubsearch.UI

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import id.chainlizard.githubsearch.MainActivity
import id.chainlizard.githubsearch.MyTimePicker
import id.chainlizard.githubsearch.R
import id.chainlizard.githubsearch.TypeList


class SettingFragment : Fragment() {

    lateinit var dialogTimePicker: DialogFragment
    lateinit var openDialogButton: Button
    companion object{

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_setting, container, false)
        openDialogButton = root.findViewById<Button>(R.id.setTime)
        openDialogButton.setOnClickListener {
            dialogTimePicker = MyTimePicker()
            dialogTimePicker.show(requireActivity().supportFragmentManager, "My Time Picker")
        }
        val jam = TypeList.readSharedPreference(requireActivity(), MyTimePicker.KEY_HOUR, 9)
        val menit = TypeList.readSharedPreference(requireActivity(), MyTimePicker.KEY_MINUTE, 0)
        val txt = "$jam:$menit"
        openDialogButton.text = txt


        return root
    }
}