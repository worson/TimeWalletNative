package app.worson.timewallet.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import app.worson.timewallet.db.dao.EventTypeEntityDao
import app.worson.timewallet.db.entity.TimeEventEntity
import app.worson.timewallet.db.entity.SubTimeEventEntity
import app.worson.timewallet.db.entity.TaskEntity
import app.worson.timewallet.db.config.TimeWalletDbInit
import app.worson.timewallet.db.dao.TimeRecordEntityDao
import app.worson.timewallet.db.entity.TimeRecordEntity
import app.worson.timewallet.utils.GlobalContext
import com.worson.lib.log.L

/**
 */
@Database(
    entities = [TimeEventEntity::class, SubTimeEventEntity::class, TaskEntity::class, TimeRecordEntity::class],
    version = 1,
    exportSchema = true
)

abstract class TimeWalletDb : RoomDatabase(){

    abstract fun getEventEntityDao(): EventTypeEntityDao
    abstract fun getTimeRecordEntityDao(): TimeRecordEntityDao

    companion object {
        val  TAG = "TimeWalletDb"
        val instance = Holder.INSTANCE
        private const val MIGRATION_TAG = "migration"
    }


    private object Holder {
        val INSTANCE = Room.databaseBuilder(
            GlobalContext.instance,
            TimeWalletDb::class.java,
            "time_wallet.db"
        )
            .addCallback(object : Callback() {

                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    L.i(TAG, "time_wallet.db onCreate: ,version=${db.version}")
                    TimeWalletDbInit.onCreate(db)

                }
            })
            .addMigrations(
            )
            .build()
    }
}