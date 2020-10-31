package app.worson.timewallet.test.module.workmanager

import android.content.Context
import androidx.work.*
import com.worson.lib.log.L
import java.time.Duration
import java.util.concurrent.TimeUnit

/**
 * @author wangshengxing  10.31 2020
 */
class TimerWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    val TAG = "TimerWorker"


    override fun doWork(): Result {
        L.i(TAG, "doWork: TimerWorker ")
        return Result.success()
    }

    companion object{
        fun start(){
            val builder=PeriodicWorkRequest.Builder(TimerWorker::class.java,PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS ,
                TimeUnit.MILLISECONDS)
            val manager= WorkManager.getInstance()
            manager.enqueue(builder.build())
        }
    }

}