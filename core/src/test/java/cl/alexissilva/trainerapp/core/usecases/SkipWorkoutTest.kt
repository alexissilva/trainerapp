package cl.alexissilva.trainerapp.core.usecases

import cl.alexissilva.trainerapp.core.data.WorkoutRepository
import cl.alexissilva.trainerapp.core.domain.WorkoutStatus
import cl.alexissilva.trainerapp.core.testutils.DummyData
import cl.alexissilva.trainerapp.core.testutils.fixedClock
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.argThat
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.time.Clock
import java.time.LocalDate
import java.time.ZoneId

class SkipWorkoutTest {
    private val dummyWorkout = DummyData.workout
    private val fakeCurrentDate = LocalDate.of(2021, 1, 1)

    private lateinit var skipWorkout: SkipWorkout
    private lateinit var repository: WorkoutRepository


    @Before
    fun setUp() {
        repository = mock()
        skipWorkout = SkipWorkout(repository, fixedClock(fakeCurrentDate))
    }

    @Test
    fun skipWorkout_createSkippedWorkoutLog_withClock() = runBlocking {
        skipWorkout(dummyWorkout)
        verify(repository).saveWorkoutLog(argThat {
            this.workoutId == dummyWorkout.id && this.status == WorkoutStatus.SKIPPED
                    && this.date.toLocalDate().isEqual(fakeCurrentDate)
        })
    }
}