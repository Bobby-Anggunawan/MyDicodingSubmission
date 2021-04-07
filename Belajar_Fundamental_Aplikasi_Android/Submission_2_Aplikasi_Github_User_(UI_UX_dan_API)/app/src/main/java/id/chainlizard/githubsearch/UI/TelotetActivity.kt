package id.chainlizard.githubsearch.UI

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.SwipeLayout.SwipeListener
import id.chainlizard.githubsearch.R
import id.chainlizard.githubsearch.TypeList
import java.util.*

class TelotetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_telotet)

        val mp = MediaPlayer.create(this, R.raw.woz_standby_loop)
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

        //mengatur swipe layout
        val mySwipe = findViewById<SwipeLayout>(R.id.mySwipe)
        mySwipe.setShowMode(SwipeLayout.ShowMode.LayDown)
        mySwipe.addDrag(SwipeLayout.DragEdge.Bottom, findViewById(R.id.bottom_wrapper))

        mySwipe.addSwipeListener(object : SwipeListener {
            override fun onClose(layout: SwipeLayout) {
                //when the SurfaceView totally cover the BottomView.
            }

            override fun onUpdate(layout: SwipeLayout, leftOffset: Int, topOffset: Int) {
                //you are swiping.
            }

            override fun onStartOpen(layout: SwipeLayout) {}
            override fun onOpen(layout: SwipeLayout) {
                finish()
            }

            override fun onStartClose(layout: SwipeLayout) {}
            override fun onHandRelease(layout: SwipeLayout, xvel: Float, yvel: Float) {
                //when user's hand released.
            }
        })
    }
}