package cl.alexissilva.trainerapp.core.usecases

import cl.alexissilva.trainerapp.core.data.WorkoutRepository
import cl.alexissilva.trainerapp.core.testutils.DummyData
import cl.alexissilva.trainerapp.core.testutils.fixedClock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.argThat
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.time.LocalDate

@ExperimentalCoroutinesApi
class SaveWorkoutLogTest {

    private val dummyWorkoutLog = DummyData.workoutLog
    private val fakeCurrentDate = LocalDate.of(2021, 1, 1)

    private lateinit var saveWorkoutLog: SaveWorkoutLog
    private lateinit var repository: WorkoutRepository


    @Before
    fun setUp() {
        repository = mock()
        saveWorkoutLog = SaveWorkoutLog(repository, fixedClock(fakeCurrentDate))
    }

    @Test
    fun saveWorkoutLog_inRepository_withClock() = runBlockingTest {

        saveWorkoutLog(dummyWorkoutLog)
        verify(repository).saveWorkoutLog(argThat {
            this.id == dummyWorkoutLog.id && this.date.toLocalDate().isEqual(fakeCurrentDate)
        })
    }

}