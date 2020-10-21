package app.worson.timewallet.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.worson.timewallet.db.entity.TimeEventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventTypeEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEvent(event: TimeEventEntity)

    @Query("DELETE FROM TimeEventEntity WHERE uid = :uid AND id = :id")
    suspend fun deleteEvent(uid: String, id: String)

    @Query("SELECT * FROM TimeEventEntity WHERE uid = :uid")
    fun queryEventsFlow(uid: String): Flow<MutableList<TimeEventEntity>>

    @Query("SELECT * FROM TimeEventEntity WHERE uid = :uid")
    fun queryLiveEvents(uid: String): LiveData<List<TimeEventEntity>>

    @Query("SELECT * FROM TimeEventEntity WHERE uid = :uid")
    suspend fun queryEvents(uid: String): MutableList<TimeEventEntity>

    @Query("SELECT * FROM TimeEventEntity WHERE uid = :uid AND id = :id")
    suspend fun queryById(uid: String, id: String): TimeEventEntity?

}
