package app.worson.timewallet.page.service

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import androidx.core.content.ContextCompat
import com.worson.lib.appbasic.application.GlobalContext
import com.worson.lib.log.L

/**
 * @author worson  10.28 2020
 */
object TaskRecordServiceManager {
    val  TAG = "TaskRecordServiceManager"

    private val mRecordServiceIntent
        get() = TaskRecordService.newIntent(GlobalContext.instance)

    private var serviceRunning = false

    private val mConn = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            L.i(TAG) {"onServiceDisconnected $name"}
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            L.i(TAG) {"onServiceConnected $name"}
        }

    }

    fun bindServer() {
        L.i(TAG, "bindServer: ")
        if (serviceRunning) {
            L.w(TAG, "bindServer: serviceRunning $serviceRunning")
            return
        }

        ContextCompat.startForegroundService(GlobalContext.instance, mRecordServiceIntent)
        GlobalContext.instance.bindService(mRecordServiceIntent, mConn, Context.BIND_AUTO_CREATE)

        serviceRunning = true
    }

    fun unbindServer() {
        L.i(TAG, "unbindServer: ")
        if (!serviceRunning) {
            L.w(TAG, "unbindServer: serviceRunning $serviceRunning ")
            return
        }

        GlobalContext.instance.unbindService(mConn)
        GlobalContext.instance.stopService(mRecordServiceIntent)

        serviceRunning = false
    }


}