package cl.alexissilva.trainerapp.framework.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import cl.alexissilva.trainerapp.domain.WorkoutStatus
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
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Named

@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(AndroidJUnit4::class)
class WorkoutDaoTest {
    private val workoutEntity = WorkoutEntity("workout", "workout", LocalDate.MIN, emptyList(), WorkoutStatus.PENDING)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("TestDb")
    lateinit var database: AppDatabase
    private lateinit var workoutDao: WorkoutDao

    @Before
    fun setUp() {
        hiltRule.inject()
        workoutDao = database.workoutDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertsWorkoutEntity() = runBlockingTest {
        workoutDao.insert(workoutEntity)

        val workouts = workoutDao.getAll().first()
        assertThat(workouts).contains(workoutEntity)
    }

    @Test
    fun updateWorkoutEntity() = runBlockingTest {
        val dummyEntity2 = WorkoutEntity(workoutEntity.id, "dummy2", LocalDate.MIN, emptyList(), WorkoutStatus.DONE)
        workoutDao.insert(workoutEntity)
        workoutDao.insert(dummyEntity2)

        val workouts = workoutDao.getAll().first()
        assertThat(workouts).containsExactly(dummyEntity2)
    }


    @Test
    fun insertManyWorkoutEntities() = runBlockingTest {
        val dummyEntity2 = WorkoutEntity("dummy2", "dummy2", LocalDate.MIN, emptyList(), WorkoutStatus.PENDING)
        workoutDao.insert(listOf(workoutEntity, dummyEntity2))

        val workouts = workoutDao.getAll().first()
        assertThat(workouts).containsExactly(workoutEntity, dummyEntity2)
    }

    @Test
    fun deletesWorkoutEntity() = runBlockingTest {
        workoutDao.insert(workoutEntity)
        workoutDao.delete(workoutEntity)

        val workouts = workoutDao.getAll().first()
        assertThat(workouts).doesNotContain(workoutEntity)
    }

    @Test
    fun getsWorkoutById() = runBlockingTest {
        workoutDao.insert(workoutEntity)

        val workout = workoutDao.getById(workoutEntity.id).first()
        assertThat(workout).isEqualTo(workoutEntity)
    }

    @Test
    fun getsNullWhenNotFoundWorkoutById() = runBlockingTest {
        val workout = workoutDao.getById(workoutEntity.id).first()
        assertThat(workout).isNull()
    }
}