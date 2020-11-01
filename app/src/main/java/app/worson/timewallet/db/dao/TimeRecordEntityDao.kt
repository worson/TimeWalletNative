package app.worson.timewallet.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.worson.timewallet.db.entity.TimeRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeRecordEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrReplace(event: TimeRecordEntity)

    @Query("DELETE FROM TimeRecordEntity WHERE uid = :uid AND id = :id")
    suspend fun deleteEvent(uid: String, id: Int)

    @Query("SELECT * FROM TimeRecordEntity WHERE uid = :uid AND dayTimestamp = :dayTimestamp")
    fun queryEventsFlow(uid: String,dayTimestamp:Int): Flow<MutableList<TimeRecordEntity>>

    @Query("SELECT * FROM TimeRecordEntity WHERE uid = :uid AND dayTimestamp = :dayTimestamp")
    fun queryEvents(uid: String,dayTimestamp:Int): MutableList<TimeRecordEntity>

    @Query("SELECT * FROM TimeRecordEntity WHERE uid = :uid AND id = :id")
    suspend fun queryById(uid: String, id: String): TimeRecordEntity?

    @Query("SELECT * FROM TimeRecordEntity WHERE uid = :uid AND startTime = :startTime")
    suspend fun queryByStartTime(uid: String, startTime: Long): TimeRecordEntity?

    @Query("SELECT * FROM TimeRecordEntity WHERE uid = :uid AND id = :id")
    fun queryFlowById(uid: String,id:Int): Flow<TimeRecordEntity>


    @Query("SELECT * FROM TimeRecordEntity WHERE uid = :uid AND endTime = 0")
    suspend fun queryNotEnd(uid: String): List<TimeRecordEntity>



}
