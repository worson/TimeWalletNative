package app.worson.timewallet.page.service

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import app.worson.timewallet.R
import com.worson.lib.log.L


/**
 * @author wangshengxing  10.31 2020
 */
class TimeEventUpdateService: RemoteViewsService() {
    override fun onCreate() {
        super.onCreate()
        L.i(TAG, "onCreate: ")
    }

    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return ListRemoteViewsFactory(this.getApplicationContext(), intent)
        L.i(TAG, "onGetViewFactory: ")
    }


    internal class ListRemoteViewsFactory(context: Context, intent: Intent) :
        RemoteViewsFactory {
        private val mContext: Context
        private val mList: MutableList<String> = mutableListOf("1","2","3")
        override fun onCreate() {}
        override fun onDataSetChanged() {}
        override fun onDestroy() {
        }

        override fun getCount(): Int {
            return mList.size
        }

        override fun getViewAt(position: Int): RemoteViews{
            val content = mList[position]
            val rv = RemoteViews(mContext.getPackageName(), R.layout.event_type_list_notification)
            rv.setTextViewText(R.id.title,content)
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

        init {
            mContext = context
        }
    }

    companion object{
        val TAG="TimeEventUpdateService"
    }
}