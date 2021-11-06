package cl.alexissilva.trainerapp.framework.database.workoutlog

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.alexissilva.trainerapp.domain.WorkoutStatus
import cl.alexissilva.trainerapp.framework.database.AppDatabase
import cl.alexissilva.trainerapp.testutils.DummyData
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(AndroidJUnit4::class)
class WorkoutLogDaoTest {

    private val logEntity = DummyData.workoutLogEntity
    private val logEntity2 = DummyData.workoutLogEntity2

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("TestDb")
    lateinit var database: AppDatabase
    private lateinit var dao: WorkoutLogDao

    @Before
    fun setUp() {
        hiltRule.inject()
        dao = database.workoutLogDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun savesLog() = runBlockingTest {
        dao.save(logEntity)
        val logs = dao.getAll().first()
        assertThat(logs).contains(logEntity)
    }

    @Test
    fun deletesLog() = runBlockingTest {
        dao.save(logEntity)
        dao.delete(logEntity)
        val logs = dao.getAll().first()
        assertThat(logs).isEmpty()
    }


    @Test
    fun getsLogById() = runBlockingTest {
        dao.save(logEntity)
        val log = dao.getById(logEntity.id).first()
        assertThat(log).isEqualTo(logEntity)
    }

    @Test
    fun getsLastLogByWorkoutId() = runBlockingTest {
        val logSameWorkout = WorkoutLogEntity(
            "logSameWorkout",
            logEntity.workoutId,
            WorkoutStatus.UNKNOWN,
            LocalDateTime.MAX,
            null,
            emptyList()
        )
        dao.save(logEntity)
        dao.save(logSameWorkout)
        val log = dao.getLastByWorkoutId(logEntity.workoutId).first()
        assertThat(log).isEqualTo(logSameWorkout)
    }

    @Test
    fun getsAllLogs() = runBlockingTest {
        dao.save(logEntity)
        dao.save(logEntity2)
        val logs = dao.getAll().first()
        assertThat(logs).containsExactly(logEntity, logEntity2)
    }
}