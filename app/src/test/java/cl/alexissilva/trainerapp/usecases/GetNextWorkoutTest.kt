package cl.alexissilva.trainerapp.usecases

import androidx.test.filters.SmallTest
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
class GetNextWorkoutTest {
    private val workout = DummyData.workout
    private val workout2 = DummyData.workout2

    private lateinit var getNextWorkout: GetNextWorkout

    @Before
    fun setUp() {
        val getUpcomingWorkouts = mock<GetUpcomingWorkouts> {
            on { invoke() } doReturn flowOf(listOf(workout, workout2))
        }
        getNextWorkout = GetNextWorkout(getUpcomingWorkouts)
    }

    @Test
    fun getsUpcomingWorkoutWithMinDay() = runBlockingTest {
        val nextWorkout = getNextWorkout().first()
        assertThat(nextWorkout).isEqualTo(workout)
    }
}