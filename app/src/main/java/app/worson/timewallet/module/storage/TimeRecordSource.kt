package app.worson.timewallet.module.storage

import app.worson.timewallet.db.TimeWalletDb
import app.worson.timewallet.db.entity.TimeRecordEntity
import app.worson.timewallet.utils.time.CallendarUtil

/**
 * @author worson  10.27 2020
 */
object TimeRecordSource {
    private val uid by lazy { AccountSettings.uid }

    val dao by  lazy{
        TimeWalletDb.instance.getTimeRecordEntityDao()
    }

    suspend fun startTimeRecord(startTime:Long=System.currentTimeMillis(),typeId:Int=1,thing: String=""):TimeRecordEntity?{
        val dayTimestamp = CallendarUtil.differDay(startTime)
        val day = CallendarUtil.dayFormatString(startTime)
        var record = TimeRecordEntity(id = 0, uid=uid , day=day, dayTimestamp=dayTimestamp, distractCount=0, endTime=0, estimatedTime=0, startTime=startTime,thing= thing, typeId=typeId)
        dao.addOrReplace(record)
        val resultRecord=dao.queryByStartTime(uid, startTime)
        return resultRecord
    }



}