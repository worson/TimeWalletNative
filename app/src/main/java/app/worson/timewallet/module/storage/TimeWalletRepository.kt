package app.worson.timewallet.module.storage

import app.worson.timewallet.db.TimeWalletDb

/**
 * 说明:
 * @author worson  10.19 2020
 */
object TimeWalletRepository {
    val eventDao by  lazy{
        TimeWalletDb.instance.getEventEntityDao()
    }

    val recordDao by  lazy{
        TimeWalletDb.instance.getTimeRecordEntityDao()
    }



}
