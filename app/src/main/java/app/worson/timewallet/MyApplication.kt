package app.worson.timewallet

import android.app.Application
import android.util.Log
import com.worson.lib.log.L


/**
 * 说明:
 * @author wangshengxing  07.24 2020
 */
class MyApplication: Application() {
    val TAG = "MyApplication"

    override fun onCreate() {
        super.onCreate()
        L.init(Log.DEBUG)
        L.i(TAG, { "onCreate: " })
    }

}