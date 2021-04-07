package id.chainlizard.githubsearch.UI

import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import id.chainlizard.githubsearch.R
import id.chainlizard.githubsearch.TypeList
import id.chainlizard.githubsearch.UI.Fragment.SettingFragment
import java.util.*

class MyTimePickerDialog: DialogFragment(), TimePickerDialog.OnTimeSetListener {

    companion object{
        const val KEY_HOUR = "jamAlarm"
        const val KEY_MINUTE = "menitAlarm"

        fun startAlarm(kalender: Calendar, context: Context){
            val managerAlarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val myIntent = Intent(context, TelotetActivity::class.java)
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            val myPendingIntent = PendingIntent.getActivity(context, 100, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            managerAlarm.setRepeating(AlarmManager.RTC_WAKEUP, kalender.timeInMillis, AlarmManager.INTERVAL_DAY ,myPendingIntent)
            Toast.makeText(context, context.getString(R.string.alarm_is_set), Toast.LENGTH_LONG).show()
        }

        fun cancelAlarm(context: Context){
            val managerAlarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val myIntent = Intent(context, TelotetActivity::class.java)
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            val myPendingIntent = PendingIntent.getActivity(context, 100, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            managerAlarm.cancel(myPendingIntent)
            Toast.makeText(context, context.getString(R.string.alarm_unset), Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val jam = TypeList.readSharedPreference(requireActivity(), KEY_HOUR, 9)
        val menit = TypeList.readSharedPreference(requireActivity(), KEY_MINUTE, 0)
        return TimePickerDialog(activity, this, jam, menit, android.text.format.DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val kalender = Calendar.getInstance()
        kalender.set(Calendar.HOUR_OF_DAY, hourOfDay)
        kalender.set(Calendar.MINUTE, minute)
        kalender.set(Calendar.SECOND, 0)

        TypeList.writeSharedPreference(requireActivity(), KEY_HOUR, hourOfDay)
        TypeList.writeSharedPreference(requireActivity(), KEY_MINUTE, minute)
        SettingFragment.model.setTime(requireActivity())
        startAlarm(kalender, requireContext())
    }
}