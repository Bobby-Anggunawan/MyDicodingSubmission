package id.chainlizard.consumerapp.UI

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import id.chainlizard.consumerapp.Database
import id.chainlizard.consumerapp.R
import id.chainlizard.consumerapp.UI.Fragment.FavoriteFragment
import id.chainlizard.consumerapp.ViewModel.Favorite
import java.util.*


class MainActivity : AppCompatActivity() {
    companion object{
        lateinit var navController: NavController
        lateinit var mainLayout: CoordinatorLayout
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment)
        mainLayout = findViewById(R.id.mainLayout)

        FavoriteFragment.model = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            Favorite::class.java
        )
        //content provider
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                FavoriteFragment.model.loadUsers(this@MainActivity)
            }
        }
        contentResolver.registerContentObserver(Database.CONTENT_URI, true, myObserver)

    }
}