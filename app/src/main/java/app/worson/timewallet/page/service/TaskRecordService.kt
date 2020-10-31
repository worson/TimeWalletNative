package app.worson.timewallet.page.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import app.worson.timewallet.R
import app.worson.timewallet.module.notification.BaseNotificationService
import app.worson.timewallet.module.notification.NotificationItem
import app.worson.timewallet.module.notification.RemoteViewsEventHelper
import app.worson.timewallet.page.MainActivity
import com.worson.lib.appbasic.android.data.bundleStrInfo
import com.worson.lib.appbasic.view.resName
import com.worson.lib.log.L

class TaskRecordService : BaseNotificationService() {


    private val mRecordBinder = RecordBinder()

    override fun getNotifacationItem(): NotificationItem {
        return NotificationItem(
            NotificationConsts.ID_RECORDING,
            NotificationConsts.CHANNEL_RECORD,
            "时间钱包",
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


        val remoteView=RemoteViews(packageName,R.layout.notification_time_record)
        mRemoteViewsEventHelper=RemoteViewsEventHelper(this,remoteView){newIntent(this)}
        setOnclickListener(R.id.vgRoot,handleEventListener)
        setOnclickListener(R.id.tvTimeType,handleEventListener)
        setOnclickListener(R.id.ivCtrl,handleEventListener)
        setOnclickListener(R.id.ivPre,handleEventListener)
        setOnclickListener(R.id.ivNext,handleEventListener)
        val builder=setNotifacation(remoteView = remoteView)
        startForeground(builder.build())
        L.i(TAG, "onCreate")

    }



    val handleEventListener = {
        id:Int ->
        L.d(TAG) { "handleEventListener:${id.resName()} " }
        when(id){
            R.id.tvTimeType -> {
                TimeEventServiceManager.bindServer()
            }
        }
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        L.i(TAG, "onStartCommand intent=${intent?.bundleStrInfo()}, flags=$flags,startId=$startId")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        L.i(TAG, "onDestroy")
        super.onDestroy()
    }


    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        L.d(TAG) { "onBind: " }
        return mRecordBinder
    }


    inner class RecordBinder : Binder() {

    }

    companion object {


        fun newIntent(context: Context): Intent {
            return Intent(context, TaskRecordService::class.java)
        }

        const val TAG = "RecordService"
    }

}
