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
    suspend fun addEvent(event: TimeRecordEntity)

    @Query("DELETE FROM TimeRecordEntity WHERE uid = :uid AND id = :id")
    suspend fun deleteEvent(uid: String, id: String)

    @Query("SELECT * FROM TimeRecordEntity WHERE uid = :uid")
    fun queryEventsFlow(uid: String): Flow<MutableList<TimeRecordEntity>>

    @Query("SELECT * FROM TimeRecordEntity WHERE uid = :uid")
    fun queryLiveEvents(uid: String): LiveData<List<TimeRecordEntity>>

    @Query("SELECT * FROM TimeRecordEntity WHERE uid = :uid")
    suspend fun queryEvents(uid: String): MutableList<TimeRecordEntity>

    @Query("SELECT * FROM TimeRecordEntity WHERE uid = :uid AND id = :id")
    suspend fun queryById(uid: String, id: String): TimeRecordEntity?

}
