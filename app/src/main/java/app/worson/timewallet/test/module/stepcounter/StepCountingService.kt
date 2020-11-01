package app.worson.timewallet.test.module.stepcounter

/**
 * @author wangshengxing  10.31 2020
 */
import android.app.*
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.RemoteViews
import androidx.core.content.ContextCompat
import app.worson.timewallet.R
import app.worson.timewallet.module.notification.BaseNotificationService
import app.worson.timewallet.module.notification.NotificationItem
import app.worson.timewallet.module.notification.RemoteViewsEventHelper
import app.worson.timewallet.page.service.NotificationConsts
import app.worson.timewallet.page.service.TaskRecordService
import com.worson.lib.appbasic.application.GlobalContext
import com.worson.lib.log.L


class StepCountingService: BaseNotificationService(), SensorEventListener {



    companion object {
        val TAG="StepCountingService"

        fun newIntent(context: Context): Intent {
            return Intent(context, StepCountingService::class.java)
        }

        private val mIntent
            get() = newIntent(GlobalContext.instance)


        fun bindServer() {
            L.i(TAG, "bindServer: ")
            ContextCompat.startForegroundService(
                GlobalContext.instance,
                mIntent
            )

        }

        fun unbindServer() {
            L.i(TAG, "unbindServer: ")
            GlobalContext.instance.stopService(mIntent)

        }
    }

    private var totalStepCount = 0
    private var totalDetection = 0
    private var serviceRunning = false

    private lateinit var remoteView:RemoteViews

    override fun getNotifacationItem(): NotificationItem {
        return NotificationItem( NotificationConsts.ID_TIME_EVENT,
            NotificationConsts.CHANNEL_TIME_EVENT,
            "时间钱包2",
            NotificationManager.IMPORTANCE_HIGH
        )

    }

    override fun onCreate() {
        super.onCreate()
        remoteView= RemoteViews(packageName, R.layout.notification_time_record)
        remoteView.setTextViewText(R.id.tvTimeType,"${totalStepCount}")
        mRemoteViewsEventHelper=
            RemoteViewsEventHelper(this,remoteView){ TaskRecordService.newIntent(this) }
        val builder=setNotifacation(remoteView = remoteView)
        startForeground(builder.build())

        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        val detectSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

        sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, detectSensor, SensorManager.SENSOR_DELAY_NORMAL)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        serviceRunning = true
        return super.onStartCommand(intent, flags, startId)
    }



    override fun onDestroy() {
        super.onDestroy()
        serviceRunning = false
    }

    override fun onAccuracyChanged(sensor: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {

        if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
            totalStepCount = event.values[0].toInt()
        }
        else if (event?.sensor?.type == Sensor.TYPE_STEP_DETECTOR) {
            totalDetection++
        }
        remoteView.setTextViewText(R.id.tvTimeType,"${totalStepCount}")
        updateNotification()
        L.i(TAG, "onSensorChanged: totalDetection=${totalDetection},totalStepCount=${totalStepCount} ")
        broadcastSensorValues()

    }

    private fun broadcastSensorValues() {

        if (!serviceRunning) return

    }



}