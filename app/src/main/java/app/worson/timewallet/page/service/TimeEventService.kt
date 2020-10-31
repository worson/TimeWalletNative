package app.worson.timewallet.page.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import app.worson.timewallet.R
import app.worson.timewallet.module.notification.BaseNotificationService
import app.worson.timewallet.module.notification.NotificationItem
import app.worson.timewallet.module.notification.RemoteViewsEventHelper
import com.worson.lib.appbasic.view.resName
import com.worson.lib.log.L

class TimeEventService : BaseNotificationService() {


    override fun getNotifacationId(): NotificationItem {
        return NotificationItem( NotificationConsts.ID_TIME_EVENT,
            NotificationConsts.CHANNEL_TIME_EVENT,
            "时间钱包2",
            NotificationManager.IMPORTANCE_HIGH
        )

    }

    override fun onCreate() {
        super.onCreate()
        L.d(TAG) { "onCreate: " }
        lifecycle.addObserver(object : LifecycleEventObserver{
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                L.d(TAG) { "onStateChanged: ${event}" }
            }
        })


        val remoteView=RemoteViews(packageName,R.layout.notification_time_event)
        remoteView.setRemoteAdapter(R.id.lvTimeEvent,Intent(this, TimeEventUpdateService::class.java))
        mRemoteViewsEventHelper=RemoteViewsEventHelper(this,remoteView){newIntent(this)}
        val builder=setNotifacation(remoteView = remoteView)
        startForeground(builder.build())
        L.i(TAG, "onCreate")

    }

    val handleEventListener = {
        id:Int ->
        L.d(TAG) { "handleEventListener:${id.resName()} " }
        when(id){

        }
    }



    companion object {


        fun newIntent(context: Context): Intent {
            return Intent(context, TimeEventService::class.java)
        }

        const val TAG = "TimeEventService"
    }

}
