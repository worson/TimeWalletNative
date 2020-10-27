package app.worson.timewallet

import android.app.Application
import android.util.Log
import app.worson.timewallet.db.TimeWalletDb
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
        GlobalScope.launch {
            L.i(TAG, "onCreate:start ")
            /*TimeWalletDb.instance.getEventEntityDao().addEvent(EventTypeEntity(uid = "0",name = "工作",desc = "赚钱",order = 1,color = 33))*/
//            TimeWalletDb.instance.getEventEntityDao().queryById("0","3")
        }
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