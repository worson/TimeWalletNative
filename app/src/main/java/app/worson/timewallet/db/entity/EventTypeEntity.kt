package app.worson.timewallet.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.annotation.Keep

/**
 * 说明:事件类型
 * @author wangshengxing  07.24 2020
 */
@Entity
@Keep
data class EventTypeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val uid: String="0",
    val name: String,
    val desc: String,
    val order: Int,
    val color: Int
) {
}