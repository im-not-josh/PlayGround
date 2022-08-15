package nz.co.fendustries.playground.roomlocalstore

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DeviceLogEntity::class], version = 1, exportSchema = false)
abstract class LogDatabase : RoomDatabase() {
    abstract val deviceLogDao: DeviceLogDao

    companion object {
        @Volatile
        private var INSTANCE: LogDatabase? = null
        private const val LOG_DATABASE = "log_database.db"

        @Synchronized
        fun getInstance(ctx: Context): LogDatabase {
            var instance = INSTANCE
            if (instance == null) {
                instance = Room.databaseBuilder(
                    ctx.applicationContext,
                    LogDatabase::class.java,
                    LOG_DATABASE
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
            }
            return instance
        }
    }
}