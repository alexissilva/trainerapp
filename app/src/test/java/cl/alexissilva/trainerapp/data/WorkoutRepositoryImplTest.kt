package cl.alexissilva.trainerapp.data

import androidx.test.filters.SmallTest
import cl.alexissilva.trainerapp.domain.Workout
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@SmallTest
class WorkoutRepositoryImplTest {
    private val dummyWorkout = Workout("dummy", "dummy")

    private lateinit var localSource: LocalWorkoutSource
    private lateinit var remoteSource: RemoteWorkoutSource
    private lateinit var workoutRepository: WorkoutRepositoryImpl

    @Before
    fun setUp() {
        localSource = mock()
        remoteSource = mock()
        workoutRepository = WorkoutRepositoryImpl(
            localSource,
            remoteSource
        )
    }

    @Test
    fun getsLocalWorkouts() {
        val localWorkouts = flowOf(listOf(dummyWorkout))
        whenever(localSource.getAll()).thenReturn(localWorkouts)
        val repositoryWorkout = workoutRepository.getLocalWorkouts()
        assertThat(repositoryWorkout).isEqualTo(localWorkouts)
    }

    @Test
    fun getsWorkoutById() {
        val workoutFlow = flowOf(dummyWorkout)
        whenever(localSource.getWorkoutById(any())).thenReturn(workoutFlow)
        val repositoryWorkout = workoutRepository.getWorkoutById("id")
        assertThat(repositoryWorkout).isEqualTo(workoutFlow)
    }

    @Test
    fun getsRemoteWorkouts() = runBlockingTest {
        val remoteWorkouts = RemoteResult.Success(listOf(dummyWorkout))
        whenever(remoteSource.getWorkouts()).thenReturn(remoteWorkouts)
        val repositoryWorkout = workoutRepository.getRemoteWorkouts()
        assertThat(repositoryWorkout).isEqualTo(remoteWorkouts)
    }

    @Test
    fun savesWorkoutLocally() = runBlockingTest {
        workoutRepository.saveWorkout(dummyWorkout)
        verify(localSource).save(dummyWorkout)
    }

    @Test
    fun savesWorkoutsLocally() = runBlockingTest {
        val workouts = listOf(dummyWorkout)
        workoutRepository.saveWorkouts(workouts)
        verify(localSource).save(workouts)
    }

    @Test
    fun deletesLocalWorkout() = runBlockingTest {
        workoutRepository.deleteWorkout(dummyWorkout)
        verify(localSource).delete(dummyWorkout)
    }
}