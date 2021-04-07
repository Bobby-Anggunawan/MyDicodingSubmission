package id.chainlizard.githubsearch

import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.app.job.JobInfo
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import java.util.*

class MyTimePicker: DialogFragment(), TimePickerDialog.OnTimeSetListener {

    companion object{
        const val KEY_HOUR = "jamAlarm"
        const val KEY_MINUTE = "menitAlarm"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return TimePickerDialog(activity, this, 9, 0, android.text.format.DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val kalender = Calendar.getInstance()
        kalender.set(Calendar.HOUR_OF_DAY, hourOfDay)
        kalender.set(Calendar.MINUTE, minute)
        kalender.set(Calendar.SECOND, 0)

        //todo belum bisa ubah label di button
        TypeList.writeSharedPreference(requireActivity(), KEY_HOUR, hourOfDay)
        TypeList.writeSharedPreference(requireActivity(), KEY_MINUTE, minute)
        startAlarm(kalender)
    }

    fun startAlarm(kalender: Calendar){
        val managerAlarm = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val myIntent = Intent(context, TelotetActivity::class.java)
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        val myPendingIntent = PendingIntent.getActivity(context, 100, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        managerAlarm.setRepeating(AlarmManager.RTC_WAKEUP, kalender.timeInMillis, AlarmManager.INTERVAL_HALF_HOUR ,myPendingIntent)
    }

    private fun cancelAlarm(){
        val managerAlarm = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val myIntent = Intent(context, TelotetActivity::class.java)
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        val myPendingIntent = PendingIntent.getActivity(context, 100, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        managerAlarm.cancel(myPendingIntent)
    }
}