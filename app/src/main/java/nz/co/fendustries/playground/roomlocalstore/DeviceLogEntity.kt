package nz.co.fendustries.playground.roomlocalstore

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import nz.co.fendustries.playground.roomlocalstore.DeviceLogEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class DeviceLogEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Long,
    @ColumnInfo(name = TAG) val tag: String,
    @ColumnInfo(name = TIME_STAMP) val timeStamp: String,
    @ColumnInfo(name = LOG_LEVEL) val logLevel: String,
    @ColumnInfo(name = MESSAGE) val message: String
) {
    @Ignore
    constructor(
        tag: String,
        timestamp: String,
        logLevel: String,
        message: String
    ) :
            this(
                0, // initial value 0, it will auto increment. insert without defining it and also it should not be var.
                tag,
                timestamp,
                logLevel,
                message
            )

    companion object {
        internal const val TABLE_NAME = "DeviceLog"
        internal const val ID = "id"
        internal const val TAG = "tag"
        internal const val TIME_STAMP = "timeStamp"
        internal const val LOG_LEVEL = "logLevel"
        internal const val MESSAGE = "message"
        internal const val LEN_URL_MSG = 2
    }
}