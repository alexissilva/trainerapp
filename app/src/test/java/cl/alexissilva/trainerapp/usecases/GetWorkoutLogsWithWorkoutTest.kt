package cl.alexissilva.trainerapp.usecases

import cl.alexissilva.trainerapp.data.WorkoutRepository
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
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.stub

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetWorkoutLogsWithWorkoutTest {
    private val workoutLogs = listOf(DummyData.workoutLog, DummyData.workoutLog2)
    private lateinit var getWorkoutLogsWithWorkout: GetWorkoutLogsWithWorkout

    @Mock
    private lateinit var repository: WorkoutRepository

    @Before
    fun setUp() {
        repository.stub { on { getWorkoutLogsWithWorkout() } doReturn flowOf(workoutLogs) }
        getWorkoutLogsWithWorkout = GetWorkoutLogsWithWorkout(repository)
    }

    @Test
    fun getsWorkoutsLogsFromRepo_whenIsInvoked() = runBlockingTest {
        val actualLogs = getWorkoutLogsWithWorkout().first()
        assertThat(actualLogs).isEqualTo(workoutLogs)
    }
}