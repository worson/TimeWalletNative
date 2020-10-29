package app.worson.timewallet.page.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import app.worson.timewallet.R
import app.worson.timewallet.module.notification.RemoteViewsEventHelper
import app.worson.timewallet.page.MainActivity
import com.worson.lib.appbasic.android.data.bundleStrInfo
import com.worson.lib.appbasic.view.resName
import com.worson.lib.log.L

class TaskRecordService : Service() {


    private val mRecordBinder = RecordBinder()

    private var mRemoteViewsEventHelper:RemoteViewsEventHelper?=null

    override fun onCreate() {
        super.onCreate()
        L.d(TAG) { "onCreate: " }
        val clickIntent = MainActivity.newTaskRecordIntent(this)
        val pendingIntent = PendingIntent.getActivity(this, 0, clickIntent, 0)


        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_RECORD,
                "时间钱包",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager =
                getSystemService(IntentService.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
            NotificationCompat.Builder(this, CHANNEL_RECORD)
        } else {
            NotificationCompat.Builder(this)
        }

        val remoteView=RemoteViews(packageName,R.layout.notification_time_record)

        mRemoteViewsEventHelper=RemoteViewsEventHelper(this,remoteView){newIntent(this)}
        mRemoteViewsEventHelper?.setOnclickListener(R.id.vgRoot,handleEventListener)
        mRemoteViewsEventHelper?.setOnclickListener(R.id.tvTimeType,handleEventListener)
        mRemoteViewsEventHelper?.setOnclickListener(R.id.ivCtrl,handleEventListener)
        mRemoteViewsEventHelper?.setOnclickListener(R.id.ivPre,handleEventListener)
        mRemoteViewsEventHelper?.setOnclickListener(R.id.ivNext,handleEventListener)



        builder.setContentIntent(pendingIntent)
            .setLargeIcon(
                BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
            )
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(getString(R.string.app_name))
            .setContentText("点击开始新任务")
            .setCustomContentView(remoteView)
            .setWhen(System.currentTimeMillis())


        startForeground(ID_RECORDING, builder.build())
        L.i(TAG, "onCreate")

    }

    val handleEventListener = {
        id:Int ->
        L.d(TAG) { "handleEventListener:${id.resName()} " }
        when(id){

        }
    }


    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        L.i(TAG, "onStartCommand intent=${intent.bundleStrInfo()}, flags=$flags,startId=$startId")
        mRemoteViewsEventHelper?.onIntentEvent(intent)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        L.i(TAG, "onDestroy")
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder {
        L.d(TAG) { "onBind: " }
        return mRecordBinder
    }


    inner class RecordBinder : Binder() {

    }

    companion object {
        const val ID_RECORDING = 2
        const val CHANNEL_RECORD = "channel_id_record"


        const val CODE_CLICK_CTRL = 1



        fun newIntent(context: Context): Intent {
            return Intent(context, TaskRecordService::class.java)
        }
        const val TAG = "RecordService"
    }

}
