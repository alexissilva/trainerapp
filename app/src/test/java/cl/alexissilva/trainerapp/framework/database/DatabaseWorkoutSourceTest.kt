package cl.alexissilva.trainerapp.framework.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import cl.alexissilva.trainerapp.domain.Workout
import cl.alexissilva.trainerapp.domain.WorkoutStatus
import cl.alexissilva.trainerapp.utils.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import java.time.LocalDate

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
@SmallTest
class DatabaseWorkoutSourceTest {

    private val workout = Workout("dummyId", "dummyName")
    private val workoutEntity =
        WorkoutEntity("dummyId", "dummyName", LocalDate.MIN, emptyList(), WorkoutStatus.PENDING)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var workoutDao: WorkoutDao

    private lateinit var databaseSource: DatabaseWorkoutSource


    @Before
    fun setUp() {
        val workoutMap = WorkoutMap()
        databaseSource = DatabaseWorkoutSource(workoutDao, workoutMap)
    }

    @Test
    fun insertsWorkoutEntity() = runBlockingTest {
        databaseSource.save(workout)
        verify(workoutDao).insert(workoutEntity)
    }

    @Test
    fun deletesWorkoutEntity() = runBlockingTest {
        databaseSource.delete(workout)
        verify(workoutDao).delete(workoutEntity)
    }

    @Test
    fun getsAllWorkouts() = runBlockingTest {
        val workoutEntities = flowOf((listOf(workoutEntity)))
        whenever(workoutDao.getAll()).thenReturn(workoutEntities)

        val workouts = databaseSource.getAll().toList()[0]
        assertThat(workouts).containsExactly(workout)
    }

    @Test
    fun getsWorkoutById() = runBlockingTest {
        val workoutEntity = flowOf(workoutEntity)
        whenever(workoutDao.getById(any())).thenReturn(workoutEntity)

        val workout = databaseSource.getWorkoutById("id").first()
        assertThat(workout).isEqualTo(workout)
    }

    @Test
    fun getsNullWhenNotFoundWorkout() = runBlockingTest {
        whenever(workoutDao.getById(any())).thenReturn(flowOf(null))

        val workout = databaseSource.getWorkoutById("id").first()
        assertThat(workout).isNull()
    }
}