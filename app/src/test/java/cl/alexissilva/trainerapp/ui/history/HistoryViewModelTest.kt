package cl.alexissilva.trainerapp.ui.history

import androidx.test.filters.SmallTest
import cl.alexissilva.trainerapp.domain.Workout
import cl.alexissilva.trainerapp.usecases.GetPastWorkouts
import cl.alexissilva.trainerapp.utils.MainCoroutineRule
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

    private val pastWorkouts = listOf(Workout("1", "s1"))
    private lateinit var viewModel: HistoryViewModel
    private lateinit var getPastWorkouts: GetPastWorkouts

    @Before
    fun setUp() {
        getPastWorkouts = mock {
            on { invoke() } doReturn flowOf(pastWorkouts)
        }
        viewModel = HistoryViewModel(getPastWorkouts)
    }

    @Test
    fun setsPastWorkouts() {
        val pastWorkouts = viewModel.pastWorkouts.value
        assertThat(pastWorkouts).isEqualTo(pastWorkouts)
    }
}