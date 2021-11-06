package cl.alexissilva.trainerapp.usecases

import cl.alexissilva.trainerapp.data.WorkoutRepository
import cl.alexissilva.trainerapp.testutils.DummyData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class DeleteWorkoutLogsTest {

    private lateinit var deleteWorkoutLogs: DeleteWorkoutLogs

    private lateinit var repository: WorkoutRepository

    private val workoutLogs = listOf(DummyData.workoutLog, DummyData.workoutLog2)

    @Before
    fun setUp() {
        repository = mock {
            on { getWorkoutLogs() } doReturn flowOf(workoutLogs)
        }
        deleteWorkoutLogs = DeleteWorkoutLogs(repository)
    }

    @Test
    fun invoke_deleteLogs() = runBlockingTest {
        deleteWorkoutLogs()
        verify(repository).deleteWorkoutLog(DummyData.workoutLog)
        verify(repository).deleteWorkoutLog(DummyData.workoutLog2)
    }
}