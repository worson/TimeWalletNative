package app.worson.timewallet.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import app.worson.timewallet.utils.Keep
import org.jetbrains.annotations.NotNull

/**
 * 待办事务类型
 */
@Entity
@Keep
data class TaskEntity (
    @PrimaryKey(autoGenerate = true)
    @NotNull
    val id: Int = 0,
    val uid: String,
    val folderId: String,
    val name: String,
    val colorId: String
)