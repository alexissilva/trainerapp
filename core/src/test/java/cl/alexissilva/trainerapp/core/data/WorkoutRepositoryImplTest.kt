package cl.alexissilva.trainerapp.core.data

import cl.alexissilva.trainerapp.core.domain.WorkoutLog
import cl.alexissilva.trainerapp.core.testutils.DummyData
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class WorkoutRepositoryImplTest {
    //FIXME use DummyData
    private val dummyWorkout = DummyData.workout
    private val dummyWorkoutLog = DummyData.workoutLog

    private lateinit var localSource: LocalWorkoutSource
    private lateinit var remoteSource: RemoteWorkoutSource
    private lateinit var workoutLogSource: WorkoutLogSource
    private lateinit var workoutRepository: WorkoutRepositoryImpl

    private val workoutLogs = emptyFlow<List<WorkoutLog>>()
    private val workoutLog = emptyFlow<WorkoutLog?>()

    @Before
    fun setUp() {
        workoutLogSource = mock {
            on { getWorkoutLogs() } doReturn workoutLogs
            on { getWorkoutLogById(any()) } doReturn workoutLog
            on { getWorkoutLogByWorkoutId(any()) } doReturn workoutLog
        }
        localSource = mock()
        remoteSource = mock()
        workoutRepository = WorkoutRepositoryImpl(
            localSource,
            remoteSource,
            workoutLogSource
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

    @Test
    fun savesWorkoutLog_intoLogSource() = runBlockingTest {
        workoutRepository.saveWorkoutLog(dummyWorkoutLog)
        verify(workoutLogSource).saveWorkoutLog(dummyWorkoutLog)
    }

    @Test
    fun deletesWorkoutLog_fromLogSource() = runBlockingTest {
        workoutRepository.deleteWorkoutLog(dummyWorkoutLog)
        verify(workoutLogSource).deleteWorkoutLog(dummyWorkoutLog)
    }

    @Test
    fun getsWorkoutLogs_fromLogSource() {
        val repositoryWorkoutLog = workoutRepository.getWorkoutLogs()
        assertThat(repositoryWorkoutLog).isEqualTo(workoutLogs)
    }

    @Test
    fun getsWorkoutLogById_fromLogSource() {
        val repositoryWorkoutLog = workoutRepository.getWorkoutLogById("id")
        assertThat(repositoryWorkoutLog).isEqualTo(workoutLog)
    }

    @Test
    fun getsWorkoutLogByWorkoutId_fromLogSource() {
        val repositoryWorkoutLog = workoutRepository.getWorkoutLogByWorkoutId("id")
        assertThat(repositoryWorkoutLog).isEqualTo(workoutLog)
    }
}