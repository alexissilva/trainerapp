package cl.alexissilva.trainerapp.ui.workouts

import cl.alexissilva.trainerapp.core.domain.Workout
import cl.alexissilva.trainerapp.core.usecases.GetUpcomingWorkouts
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
class WorkoutsViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val workouts = listOf(
        Workout("1", "s1"),
        Workout("2", "s2"),
    )

    private lateinit var getUpcomingWorkouts: GetUpcomingWorkouts
    private lateinit var viewModel: WorkoutsViewModel

    @Before
    fun setUp() {
        getUpcomingWorkouts = mock {
            on { invoke() } doReturn flowOf(workouts)
        }
        viewModel = WorkoutsViewModel(getUpcomingWorkouts)
    }

    @Test
    fun getUpcomingWorkouts() = runBlockingTest {
        val viewModelWorkout = viewModel.workouts.first()
        assertThat(viewModelWorkout).isEqualTo(workouts)
    }
}