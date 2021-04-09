package id.chainlizard.githubsearch.UI

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.SwipeLayout.SwipeListener
import id.chainlizard.githubsearch.R
import java.util.*

class TelotetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_telotet)

        val animationArrow = findViewById<ImageView>(R.id.animasiPanah)
        Glide.with(this)
                .asGif()
                .load(R.drawable.arrow_up)
                .fitCenter()
                .into(animationArrow)
        val mp = MediaPlayer.create(this, R.raw.woz_standby_loop)
        mp.isLooping = true
        mp.start()

        val myCalendar = Calendar.getInstance()
        val jam = myCalendar.get(Calendar.HOUR_OF_DAY)
        val menit = myCalendar.get(Calendar.MINUTE)
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
        findViewById<TextView>(R.id.jamDiAlarm).text = txt

        //mengatur swipe layout(untuk mematikan alarm)
        val mySwipe = findViewById<SwipeLayout>(R.id.mySwipe)
        mySwipe.setShowMode(SwipeLayout.ShowMode.LayDown)
        mySwipe.addDrag(SwipeLayout.DragEdge.Bottom, findViewById(R.id.bottom_wrapper))

        mySwipe.addSwipeListener(object : SwipeListener {
            override fun onClose(layout: SwipeLayout) {}

            override fun onUpdate(layout: SwipeLayout, leftOffset: Int, topOffset: Int) {}

            override fun onStartOpen(layout: SwipeLayout) {}
            override fun onOpen(layout: SwipeLayout) {
                mp.stop()
                finish()
            }

            override fun onStartClose(layout: SwipeLayout) {}
            override fun onHandRelease(layout: SwipeLayout, xvel: Float, yvel: Float) {}
        })
    }
}