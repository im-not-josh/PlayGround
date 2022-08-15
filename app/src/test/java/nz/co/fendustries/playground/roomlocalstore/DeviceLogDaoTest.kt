package nz.co.fendustries.playground.roomlocalstore

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import nz.co.fendustries.playground.BuildConfig
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [31])
class DeviceLogDaoTest {

    private lateinit var logDao: DeviceLogDao
    private lateinit var db: LogDatabase

    companion object {
        private const val ID = 1L
        private const val TAG = "test"
        private const val TIME_STAMP = "12:00"
        private const val LOG_LEVEL = "too much"
        private const val MESSAGE = "be Nice"
    }

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LogDatabase::class.java
        ).allowMainThreadQueries().build()
        logDao = db.deviceLogDao
    }

    @After
    fun tearDown() {
        db.clearAllTables()
        db.close()
    }

    @Test
    fun `given DeviceLogEntity, when getAllDeviceLogs from DB, then validate result`() {
        // Given
        val logEntity =
            DeviceLogEntity(
                TAG,
                TIME_STAMP,
                LOG_LEVEL,
                MESSAGE
            )

        logDao.insertDeviceLogs(logEntity).subscribe()

        // When
        val result = logDao.getAllDeviceLogs().test()

        // Then
        result.assertValue {
            it[0].id == ID
            it[0].tag == TAG
            it[0].logLevel == LOG_LEVEL
            it[0].message == MESSAGE
            it[0].timeStamp == TIME_STAMP
        }
    }

    @Test
    fun `given DeviceLogEntity when insert to DB, then validate result`() {
        // Given
        val logEntity =
            DeviceLogEntity(
                TAG,
                TIME_STAMP,
                LOG_LEVEL,
                MESSAGE
            )
        // When
        val numberOfRows = logDao.insertDeviceLogs(logEntity).test()

        // Then
        numberOfRows.assertValue(1L)
        val actualResult = logDao.getAllDeviceLogs().test()

        actualResult.assertValue {
            it[0].id == ID
            it[0].tag == TAG
            it[0].logLevel == LOG_LEVEL
            it[0].message == MESSAGE
            it[0].timeStamp == TIME_STAMP
        }
    }

    @Test
    fun `given DeviceLogEntity, when DeleteAll form the DB, then validate result`() {
        // Given
        val logEntity =
            DeviceLogEntity(
                TAG,
                TIME_STAMP,
                LOG_LEVEL,
                MESSAGE
            )

        logDao.insertDeviceLogs(logEntity).subscribe()

        // When
        val result = logDao.deleteALLDeviceLogs().test()

        // Then
        result.assertValue(1)
    }

    @Test
    fun `given DeviceLogEntity, when Delete by id form the DB, then validate result`() {
        // Given
        val logEntity =
            DeviceLogEntity(
                TAG,
                TIME_STAMP,
                LOG_LEVEL,
                MESSAGE
            )
        logDao.insertDeviceLogs(logEntity).subscribe()

        // When
        val numberOfRows = logDao.delete(1).test()

        // Then
        numberOfRows.assertValue(1)
    }
}