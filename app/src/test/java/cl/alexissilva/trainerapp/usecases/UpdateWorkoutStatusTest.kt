package cl.alexissilva.trainerapp.usecases

import cl.alexissilva.trainerapp.data.WorkoutRepository
import cl.alexissilva.trainerapp.domain.Workout
import cl.alexissilva.trainerapp.domain.WorkoutStatus
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class UpdateWorkoutStatusTest {


    private lateinit var updateWorkoutStatus: UpdateWorkoutStatus

    private lateinit var repository: WorkoutRepository

    @Before
    fun setUp() {
        repository = mock()
        updateWorkoutStatus = UpdateWorkoutStatus(repository)
    }

    @Test
    fun invoke_updateStatus() = runBlocking {
        val pendingWorkout = Workout("1", "s1", status = WorkoutStatus.PENDING)
        updateWorkoutStatus(pendingWorkout, WorkoutStatus.DONE)

        val expectedWorkout = Workout("1", "s1", status = WorkoutStatus.DONE)
        verify(repository).saveWorkout(expectedWorkout)
    }
}