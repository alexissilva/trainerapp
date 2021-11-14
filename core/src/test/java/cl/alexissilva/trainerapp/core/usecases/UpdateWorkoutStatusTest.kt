package cl.alexissilva.trainerapp.core.usecases

import cl.alexissilva.trainerapp.core.data.WorkoutRepository
import cl.alexissilva.trainerapp.core.domain.WorkoutStatus
import cl.alexissilva.trainerapp.core.usecases.UpdateWorkoutStatus
import cl.alexissilva.trainerapp.core.testutils.DummyData
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.argThat
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.time.Clock
import java.time.LocalDate
import java.time.ZoneId

class UpdateWorkoutStatusTest {
    private val dummyWorkout = DummyData.workout
    private val fakeCurrentDate = LocalDate.of(2021, 1, 1)

    private lateinit var updateWorkoutStatus: UpdateWorkoutStatus
    private lateinit var repository: WorkoutRepository


    @Before
    fun setUp() {
        val fixedClock = Clock.fixed(
            fakeCurrentDate.atStartOfDay(ZoneId.systemDefault()).toInstant(),
            ZoneId.systemDefault()
        )
        repository = mock()
        updateWorkoutStatus = UpdateWorkoutStatus(repository, fixedClock)
    }

    @Test
    fun saveLogOfWorkout() = runBlocking {
        updateWorkoutStatus(dummyWorkout, WorkoutStatus.DONE)
        verify(repository).saveWorkoutLog(argThat {
            this.workoutId == dummyWorkout.id && this.status == WorkoutStatus.DONE
        })
    }
}