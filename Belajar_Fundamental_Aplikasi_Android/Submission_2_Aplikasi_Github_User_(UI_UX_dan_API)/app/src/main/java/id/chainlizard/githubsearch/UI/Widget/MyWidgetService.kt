package id.chainlizard.githubsearch.UI.Widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import android.widget.RemoteViewsService.RemoteViewsFactory
import com.bumptech.glide.Glide
import id.chainlizard.githubsearch.Database
import id.chainlizard.githubsearch.R
import id.chainlizard.githubsearch.TypeList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


class MyWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return StackRemoteViewsFactory(this.applicationContext, intent)
    }
}

internal class StackRemoteViewsFactory(context: Context, intent: Intent) : RemoteViewsFactory {

    private val mMyWidgetItems: MutableList<TypeList.MyWidgetItem> = ArrayList()
    private val mContext: Context
    private val mAppWidgetId: Int


    init {
        mContext = context
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID)
    }
    
    override fun onCreate() {}

    override fun onDestroy() {
        mMyWidgetItems.clear()
    }

    override fun getCount(): Int {
        return mMyWidgetItems.count()
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.getPackageName(), R.layout.item_widget)
        rv.setTextViewText(R.id.widget_item, mMyWidgetItems[position].username)
        val bitmap: Bitmap = Glide.with(mContext)
                .asBitmap()
                .load(mMyWidgetItems[position].avatar)
                .submit(512, 512)
                .get()

        rv.setImageViewBitmap(R.id.avatar_img, bitmap)

        val extras = Bundle()
        extras.putInt(MyWidgetProvider.EXTRA_ITEM, position)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.widget_item, fillInIntent)

        try {
            println("Loading view $position")
            Thread.sleep(500)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return rv
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun onDataSetChanged() {
        var storeUser: ArrayList<TypeList.User> = arrayListOf()
        runBlocking {
            val getUser = async(Dispatchers.IO) { Database.DatabaseHelper.getAllUser(mContext) }
            storeUser = getUser.await()
        }

        for (x in 0..storeUser.count()-1) {
            mMyWidgetItems.add(
                TypeList.MyWidgetItem(
                    storeUser[x].login.toString(),
                    storeUser[x].avatar_url.toString()
                )
            )
        }
    }
}