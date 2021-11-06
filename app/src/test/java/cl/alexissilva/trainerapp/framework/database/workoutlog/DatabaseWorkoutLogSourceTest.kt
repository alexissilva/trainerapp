package cl.alexissilva.trainerapp.framework.database.workoutlog

import cl.alexissilva.trainerapp.testutils.DummyData
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DatabaseWorkoutLogSourceTest {
    private val log = DummyData.workoutLog
    private val logEntity = DummyData.workoutLogEntity

    private lateinit var logSource: DatabaseWorkoutLogSource

    @Mock
    private lateinit var dao: WorkoutLogDao

    @Mock
    private lateinit var map: WorkoutLogMap

    @Before
    fun setUp() {
        whenever(map.toWorkoutLogEntity(log)).thenReturn(logEntity)
        whenever(map.fromWorkoutLogEntity(logEntity)).thenReturn(log)
        logSource = DatabaseWorkoutLogSource(dao, map)
    }

    @Test
    fun savesLogEntity_whenSaveLog() = runBlockingTest {
        logSource.saveWorkoutLog(log)
        verify(dao).save(logEntity)
    }

    @Test
    fun deleteLogEntity_whenDeleteLog() = runBlockingTest {
        logSource.deleteWorkoutLog(log)
        verify(dao).delete(logEntity)
    }

    @Test
    fun mapsLogs_whenGetLogs() = runBlockingTest {
        whenever(dao.getAll()).thenReturn(flowOf(listOf(logEntity, logEntity)))
        val logs = logSource.getWorkoutLogs().first()
        assertThat(logs).containsExactly(log, log)
    }

    @Test
    fun mapsLog_whenGetWorkoutLogById() = runBlockingTest {
        whenever(dao.getById("id")).thenReturn(flowOf(logEntity))
        val actualLog = logSource.getWorkoutLogById("id").first()
        assertThat(actualLog).isEqualTo(log)
    }

    @Test
    fun mapsLog_whenGetLogByWorkoutId() = runBlockingTest {
        whenever(dao.getLastByWorkoutId("workout")).thenReturn(flowOf(logEntity))
        val actualLog = logSource.getWorkoutLogByWorkoutId("workout").first()
        assertThat(actualLog).isEqualTo(log)
    }
}