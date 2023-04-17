package cl.alexissilva.trainerapp.ui.workoutdetails

import androidx.lifecycle.SavedStateHandle
import androidx.test.filters.SmallTest
import cl.alexissilva.trainerapp.core.domain.Workout
import cl.alexissilva.trainerapp.core.usecases.GetLocalWorkout
import cl.alexissilva.trainerapp.testutils.MainCoroutineRule
import cl.alexissilva.trainerapp.testutils.TestCoroutineContextProvider
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
@SmallTest
class WorkoutDetailsViewModelTest {
    private val workoutId = "1"
    private val workout = Workout(workoutId, "s1")

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    private val contextProvider = TestCoroutineContextProvider()

    private lateinit var viewModel: WorkoutDetailsViewModel

    @Before
    fun setUp() {
        val getLocalWorkout = mock<GetLocalWorkout> {
            on { invoke(workoutId) } doReturn flowOf(workout)
        }
        viewModel = WorkoutDetailsViewModel(contextProvider, getLocalWorkout)
    }

    @Test
    fun loadLocalWorkout() = runTest {
        viewModel.loadWorkout(workoutId)
        val viewModelWorkout = viewModel.workout.first()
        assertThat(viewModelWorkout).isEqualTo(workout)
    }
}