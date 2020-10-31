package app.worson.timewallet.db.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

/**
 * 时间记录类型
 */
@Entity
@Keep
data class TimeRecordEntity (
    @PrimaryKey(autoGenerate = true)
    @NotNull
    val id: Int = 0,
    val uid: String,
    val day: String,
    val dayTimestamp: Long,
    val distractCount: Int,
    val endTime: Long,
    val estimatedTime: Long,
    val startTime: Long,
    val thing: String?,
    val typeId: Int,
){
    fun isTasking()= endTime==0L

    fun isTimeTask()= estimatedTime!=0L

    fun costTimeMs() = System.currentTimeMillis()-startTime
}