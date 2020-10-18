package app.worson.timewallet.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import app.worson.timewallet.page.utils.Keep

/**
 * 说明:子事件类型
 * @author wangshengxing  07.24 2020
 */
@Entity
@Keep
data class SubEventTypeEntity(
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