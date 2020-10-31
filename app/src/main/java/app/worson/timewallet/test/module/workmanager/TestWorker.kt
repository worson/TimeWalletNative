package app.worson.timewallet.test.module.workmanager

import android.content.Context
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.worson.lib.log.L

/**
 * @author wangshengxing  10.31 2020
 */
class TestWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    val TAG = "TestWorker"


    override fun doWork(): Result {
        L.i(TAG, "doWork: ")
        return Result.success()
    }

    companion object{
        fun start(){
            val manager= WorkManager.getInstance()
            manager.beginWith(OneTimeWorkRequest.from(TestWorker::class.java))
                .enqueue()
        }
    }

}