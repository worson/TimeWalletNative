package app.worson.timewallet.db.import

import androidx.room.PrimaryKey
import app.worson.timewallet.db.TimeWalletDb
import app.worson.timewallet.db.entity.TimeRecordEntity
import app.worson.timewallet.module.const.UserConst
import com.worson.lib.appbasic.application.GlobalContext
import com.blankj.utilcode.util.GsonUtils
import com.google.gson.annotations.SerializedName
import com.worson.lib.log.L
import org.jetbrains.annotations.NotNull
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

/**
 * @author worson  10.23 2020
 */

data class TLTimeRecord (
    @PrimaryKey(autoGenerate = true)
    @NotNull
    val id: Int = 0,
    val uid: String?,
    val day: String,
    val dayTimestamp: Long,
    val distractCount: Int,
    val endTime: Long,
    val estimatedTime: Long,
    val startTime: Long,
    val thing: String?,
    val typeId: Int,
){
    fun toTimeRecordEntity(): TimeRecordEntity {
        return TimeRecordEntity(id, uid ?: UserConst.DEFAULT_UID, day, dayTimestamp, distractCount, endTime, estimatedTime, startTime, thing, typeId)
    }
}

data class TLAllRecord(
    @SerializedName(value = "a")
    val timeRecords:List<TLTimeRecord>?
){

}

class TimeLogImport {
    val walletDb= TimeWalletDb.instance

    suspend fun importAssetFile(assetPath:String){
        val inputStream =
            GlobalContext.instance.assets.open(assetPath)
        import(inputStream)
    }

    suspend fun importFile(file:File){
        import(FileInputStream(file))
    }

    suspend fun import(inputStream:InputStream){
        val defaultDataStr = String(inputStream.readBytes())
        val defaultData =
            GsonUtils.fromJson(defaultDataStr, TLAllRecord::class.java)
        defaultData.timeRecords?.let {
            importTimeRecord(it)
        }
    }

    private suspend fun importTimeRecord(timeRecords:List<TLTimeRecord>){
        L.i(TAG, "importTimeRecord: timeRecords size ${timeRecords.size}, first : ${timeRecords.firstOrNull()}")
        val dao=walletDb.getTimeRecordEntityDao()
        timeRecords.forEach {
            dao.addOrReplace(it.toTimeRecordEntity())
        }
    }

    companion object{
        val  TAG = "TimeLogImport"
    }
}