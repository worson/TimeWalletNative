package app.worson.timewallet.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.annotation.Keep

/**
 * 说明:子事件类型
 * @author wangshengxing  07.24 2020
 */
@Entity
@Keep
data class SubTimeEventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val parent_id:Int,
    val uid: String,
    val name: String,
    val desc: String,
    val order: Int,
    val color: String
) {
}