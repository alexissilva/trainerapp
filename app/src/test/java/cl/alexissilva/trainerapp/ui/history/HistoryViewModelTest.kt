package cl.alexissilva.trainerapp.ui.history

import androidx.test.filters.SmallTest
import cl.alexissilva.trainerapp.testutils.DummyData
import cl.alexissilva.trainerapp.usecases.GetWorkoutLogsWithWorkout
import cl.alexissilva.trainerapp.testutils.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock


@ExperimentalCoroutinesApi
@SmallTest
class HistoryViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val workoutLogs = listOf(DummyData.workoutLog, DummyData.workoutLog2)
    private lateinit var viewModel: HistoryViewModel
    private lateinit var getWorkoutLogsWithWorkout: GetWorkoutLogsWithWorkout

    @Before
    fun setUp() {
        getWorkoutLogsWithWorkout = mock {
            on { invoke() } doReturn flowOf(workoutLogs)
        }
        viewModel = HistoryViewModel(getWorkoutLogsWithWorkout)
    }

    @Test
    fun setsPastWorkouts() {
        val pastWorkouts = viewModel.workoutLogs.value
        assertThat(pastWorkouts).isEqualTo(pastWorkouts)
    }
}