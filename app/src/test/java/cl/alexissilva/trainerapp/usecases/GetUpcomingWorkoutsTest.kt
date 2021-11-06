package cl.alexissilva.trainerapp.usecases

import androidx.test.filters.SmallTest
import cl.alexissilva.trainerapp.data.WorkoutRepository
import cl.alexissilva.trainerapp.domain.Workout
import cl.alexissilva.trainerapp.domain.WorkoutLog
import cl.alexissilva.trainerapp.domain.WorkoutStatus
import cl.alexissilva.trainerapp.testutils.DummyData
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
@SmallTest
class GetUpcomingWorkoutsTest {
    private val workout = DummyData.workout
    private val workout2 = DummyData.workout2
    private val workout3 = Workout("id3", "name3")
    private val workout4 = Workout("id4", "name4")

    private val log = DummyData.workoutLog
    private val logOfWorkout2 = WorkoutLog("id2", workout2.id, WorkoutStatus.UNKNOWN)
    private val logOfWorkout3 = WorkoutLog("id3", workout3.id, WorkoutStatus.DONE)
    private val logOfWorkout4 = WorkoutLog("id4", workout4.id, WorkoutStatus.SKIPPED)


    private lateinit var getUpcomingWorkouts: GetUpcomingWorkouts

    @Before
    fun setUp() {
        val workoutFlow = flowOf(listOf(workout, workout2, workout3, workout4))
        val logFlow = flowOf(listOf(log, logOfWorkout2, logOfWorkout3, logOfWorkout4))
        val repository = mock<WorkoutRepository> {
            on { getLocalWorkouts() } doReturn workoutFlow
            on { getWorkoutLogs() } doReturn logFlow
        }
        getUpcomingWorkouts = GetUpcomingWorkouts(repository)
    }

    @Test
    fun getsWorkoutWithUnknownStatus() = runBlockingTest {
        val pastWorkouts = getUpcomingWorkouts().first()
        assertThat(pastWorkouts).containsExactly(workout, workout2)
    }
}