package id.chainlizard.saltiesmovie

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import id.chainlizard.saltiesmovie.functions.Networking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}