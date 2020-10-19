package app.worson.timewallet

import android.app.Application
import android.util.Log
import app.worson.timewallet.db.TimeWalletDb
import app.worson.timewallet.db.entity.EventTypeEntity
import com.worson.lib.log.L
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


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
        GlobalScope.launch {
            L.i(TAG, "onCreate:start ")
            /*TimeWalletDb.instance.getEventEntityDao().addEvent(EventTypeEntity(uid = "0",name = "工作",desc = "赚钱",order = 1,color = 33))*/
            TimeWalletDb.instance.getEventEntityDao().queryById("0","3")
        }
    }

}