package cl.alexissilva.trainerapp.ui.workoutdetails

import androidx.lifecycle.SavedStateHandle
import androidx.test.filters.SmallTest
import cl.alexissilva.trainerapp.domain.Workout
import cl.alexissilva.trainerapp.usecases.GetLocalWorkout
import cl.alexissilva.trainerapp.testutils.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
@SmallTest
class WorkoutDetailsViewModelTest {
    private val workout = Workout("1", "s1")

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    private lateinit var viewModel: WorkoutDetailsViewModel

    @Before
    fun setUp() {
        val map = mutableMapOf(Pair("workoutId", "1")) as Map<String, *>
        val state = SavedStateHandle(map)
        val getLocalWorkout = mock<GetLocalWorkout> {
            on { invoke("1") } doReturn flowOf(workout)
        }
        viewModel = WorkoutDetailsViewModel(getLocalWorkout, state)
    }

    @Test
    fun getLocalWorkoutUsingState() = runBlockingTest {
        val workout = viewModel.workout.first()
        assertThat(workout).isEqualTo(workout)
    }
}