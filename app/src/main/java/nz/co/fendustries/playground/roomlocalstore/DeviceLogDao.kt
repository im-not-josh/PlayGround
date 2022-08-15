package nz.co.fendustries.playground.roomlocalstore

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import nz.co.fendustries.playground.roomlocalstore.DeviceLogEntity.Companion.ID
import nz.co.fendustries.playground.roomlocalstore.DeviceLogEntity.Companion.TABLE_NAME

@Dao
interface DeviceLogDao {

    @Query("SELECT * FROM $TABLE_NAME ORDER BY $ID DESC")
    fun getAllDeviceLogs(): Single<List<DeviceLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDeviceLogs(logEntity: DeviceLogEntity): Single<Long>

    @Query("DELETE FROM $TABLE_NAME")
    fun deleteALLDeviceLogs(): Single<Int>

    @Query("DELETE FROM $TABLE_NAME WHERE $ID<=:id")
    fun delete(id: Long): Single<Int>
}