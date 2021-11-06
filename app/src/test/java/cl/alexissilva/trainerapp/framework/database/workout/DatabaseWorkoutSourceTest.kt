package cl.alexissilva.trainerapp.framework.database.workout

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import cl.alexissilva.trainerapp.testutils.DummyData
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
@SmallTest
class DatabaseWorkoutSourceTest {

    private val workout = DummyData.workout
    private val workoutEntity = DummyData.workoutEntity

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var workoutDao: WorkoutDao

    @Mock
    private lateinit var workoutMap: WorkoutMap

    private lateinit var databaseSource: DatabaseWorkoutSource

    @Before
    fun setUp() {
        whenever(workoutMap.toWorkoutEntity(workout)).thenReturn(workoutEntity)
        whenever(workoutMap.fromWorkoutEntity(workoutEntity)).thenReturn(workout)
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
        whenever(workoutDao.getAll()).thenReturn(flowOf((listOf(workoutEntity, workoutEntity))))
        val workouts = databaseSource.getAll().first()
        assertThat(workouts).containsExactly(workout, workout)
    }

    @Test
    fun getsWorkoutById() = runBlockingTest {
        whenever(workoutDao.getById("id")).thenReturn(flowOf(workoutEntity))
        val workout = databaseSource.getWorkoutById("id").first()
        assertThat(workout).isEqualTo(workout)
    }

    @Test
    fun getsNullWhenNotFoundWorkout() = runBlockingTest {
        whenever(workoutDao.getById("id")).thenReturn(flowOf(null))
        val workout = databaseSource.getWorkoutById("id").first()
        assertThat(workout).isNull()
    }
}