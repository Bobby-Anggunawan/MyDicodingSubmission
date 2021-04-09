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

    private val mMyWidgetItems: MutableList<MyWidgetItem> = ArrayList()
    private val mContext: Context
    private val mAppWidgetId: Int


    init {
        mContext = context
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID)
    }
    
    override fun onCreate() {
        // In onCreate() you setup any connections / cursors to your data source. Heavy lifting,
        // for example downloading or creating content etc, should be deferred to onDataSetChanged()
        // or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.



        // We sleep for 3 seconds here to show how the empty view appears in the interim.
        // The empty view is set in the MyWidgetProvider and should be a sibling of the
        // collection view.
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        // In onDestroy() you should tear down anything that was setup for your data source,
        // eg. cursors, connections, etc.
        mMyWidgetItems.clear()
    }

    override fun getCount(): Int {
        return mMyWidgetItems.count()
    }

    override fun getViewAt(position: Int): RemoteViews {
        // position will always range from 0 to getCount() - 1.

        // We construct a remote views item based on our widget item xml file, and set the
        // text based on the position.
        val rv = RemoteViews(mContext.getPackageName(), R.layout.item_widget)
        rv.setTextViewText(R.id.widget_item, mMyWidgetItems[position].username)
        val bitmap: Bitmap = Glide.with(mContext)
                .asBitmap()
                .load(mMyWidgetItems[position].avatar)
                .submit(512, 512)
                .get()

        rv.setImageViewBitmap(R.id.avatar_img, bitmap)

        // Next, we set a fill-intent which will be used to fill-in the pending intent template
        // which is set on the collection view in MyWidgetProvider.
        val extras = Bundle()
        extras.putInt(MyWidgetProvider.EXTRA_ITEM, position)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.widget_item, fillInIntent)

        // You can do heaving lifting in here, synchronously. For example, if you need to
        // process an image, fetch something from the network, etc., it is ok to do it here,
        // synchronously. A loading view will show up in lieu of the actual contents in the
        // interim.
        try {
            println("Loading view $position")
            Thread.sleep(500)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        // Return the remote views object.
        return rv
    }

    override fun getLoadingView(): RemoteViews? {
        // You can create a custom loading view (for instance when getViewAt() is slow.) If you
        // return null here, you will get the default loading view.
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
        // This is triggered when you call AppWidgetManager notifyAppWidgetViewDataChanged
        // on the collection view corresponding to this factory. You can do heaving lifting in
        // here, synchronously. For example, if you need to process an image, fetch something
        // from the network, etc., it is ok to do it here, synchronously. The widget will remain
        // in its current state while work is being done here, so you don't need to worry about
        // locking up the widget.

        var storeUser: ArrayList<TypeList.User> = arrayListOf()
        runBlocking {
            val getUser = async(Dispatchers.IO) { Database.DatabaseHelper.getAllUser(mContext) }
            storeUser = getUser.await()
        }

        for (x in 0..storeUser.count()-1) {
            mMyWidgetItems.add(MyWidgetItem(storeUser[x].login.toString(), storeUser[x].avatar_url.toString()))
        }
    }
}