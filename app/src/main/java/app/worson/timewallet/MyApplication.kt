package app.worson.timewallet

import android.app.Application
import android.util.Log
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import app.worson.timewallet.db.TimeWalletDb
import app.worson.timewallet.page.service.TaskRecordServiceManager
import app.worson.timewallet.page.service.TimeEventServiceManager
import app.worson.timewallet.test.module.workmanager.TestWorker
import app.worson.timewallet.test.module.workmanager.TimerWorker
import com.langogo.lib.log.internal.Platform
import com.worson.lib.log.L
import com.worson.lib.log.LogConfiguration
import com.worson.lib.log.LogLevel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * @author wangshengxing  07.24 2020
 */
class MyApplication: Application() {
    private val TAG = "MyApplication"

    override fun onCreate() {
        super.onCreate()
        initConsolePrint(true)
        L.i(TAG, { "onCreate: " })
//        TimeEventServiceManager.bindServer()
        TaskRecordServiceManager.bindServer()
        initTask()
    }

    private fun initTask() {
        L.i(TAG, "initTask: ")
        TestWorker.start()
        TimerWorker.start()
    }

    fun initConsolePrint(debug:Boolean){
        L.init(
            LogConfiguration.Builder()
                .logLevel(if (debug) LogLevel.ALL else LogLevel.DEBUG)
                .threadInfo(debug)
                .traceInfo(debug, 7)
                .addPrinter(Platform.get().defaultPrinter())
                .build()
        )
    }

}