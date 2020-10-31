package app.worson.timewallet.module.notification

import android.app.*
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LifecycleService
import app.worson.timewallet.R
import app.worson.timewallet.page.MainActivity
import app.worson.timewallet.page.service.NotificationConsts


data class NotificationItem(
    val id:Int,
    val channelId:String,
    val channelName:String,
    val channelImportance:Int
)

/**
 *
 * @author worson  10.29 2020
 */
abstract open class BaseNotificationService :LifecycleService(){

    protected var mRemoteViewsEventHelper:RemoteViewsEventHelper?=null

    protected lateinit var mNotification:Notification

    abstract protected fun getNotifacationId():NotificationItem

    protected fun pendingIntent():PendingIntent{
        val clickIntent = MainActivity.newTaskRecordIntent(this)
        val pendingIntent = PendingIntent.getActivity(this, 0, clickIntent, 0)
        return pendingIntent
    }

    protected fun notificationBuilder():NotificationCompat.Builder{
        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = getNotifacationId().let { NotificationChannel(it.channelId,it.channelName,it.channelImportance) }
            val manager =
                getSystemService(IntentService.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
            NotificationCompat.Builder(this, NotificationConsts.CHANNEL_RECORD)
        } else {
            NotificationCompat.Builder(this)
        }

        return builder

    }

    protected fun setNotifacation(builder:NotificationCompat.Builder=notificationBuilder(),remoteView:RemoteViews):NotificationCompat.Builder{
        builder.setContentIntent(pendingIntent())
            .setLargeIcon(
                BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
            )
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(getString(R.string.app_name))
            .setContentText("点击开始新任务")
            .setCustomContentView(remoteView)
            .setWhen(System.currentTimeMillis())
        return builder
    }

    protected fun startForeground(build: Notification) {
        mNotification=build
        startForeground(getNotifacationId().id,build)
    }

    protected fun updateNotification(){
        NotificationManagerCompat.from(this).notify(getNotifacationId().id,mNotification)
    }



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            mRemoteViewsEventHelper?.onIntentEvent(it)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    fun setOnclickListener(id: Int,listener: (id:Int)-> Unit){
        mRemoteViewsEventHelper?.setOnclickListener(id, listener)
    }
    fun setOnclickListener(id: Int,listener: OnclickListener?){
        mRemoteViewsEventHelper?.setOnclickListener(id, listener)
    }


}