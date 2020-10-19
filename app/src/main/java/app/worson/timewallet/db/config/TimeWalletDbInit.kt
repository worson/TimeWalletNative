package app.worson.timewallet.db.config

import androidx.sqlite.db.SupportSQLiteDatabase
import app.worson.timewallet.db.TimeWalletDb
import app.worson.timewallet.utils.GlobalContext
import com.blankj.utilcode.util.GsonUtils
import com.worson.lib.log.L
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * 说明:
 * @author worson  10.18 2020
 */
object TimeWalletDbInit {
    val  TAG = "TimeWalletDbInit"

    fun onCreate(db: SupportSQLiteDatabase){
        val version=db.version
        val valletDb=TimeWalletDb.instance
        GlobalScope.launch(Dispatchers.IO) {
            val inputStream =
                GlobalContext.instance.assets.open("default/default_time_wallet_data.json")
            val defaultDataStr = String(inputStream.readBytes())
            val defaultData =
                GsonUtils.fromJson(defaultDataStr, DefaultTimeWalletData::class.java)
            L.i(TimeWalletDb.TAG, "onCreate: ${defaultData}")
            if (version==0) {
                val dao=valletDb.getEventEntityDao()
                defaultData.default_events?.forEach {
                    dao.addEvent(it.copy(uid = ""))
                }

            }
        }

    }
}