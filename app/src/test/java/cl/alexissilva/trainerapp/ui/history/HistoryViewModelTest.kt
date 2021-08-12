package cl.alexissilva.trainerapp.ui.history

import androidx.test.filters.SmallTest
import cl.alexissilva.trainerapp.domain.Session
import cl.alexissilva.trainerapp.usecases.GetPastSessions
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

    private val dummyPastSessions = listOf(Session("1", "s1"))
    private lateinit var viewModel: HistoryViewModel
    private lateinit var getPastSessions: GetPastSessions

    @Before
    fun setUp() {
        getPastSessions = mock {
            on { invoke() } doReturn flowOf(dummyPastSessions)
        }
        viewModel = HistoryViewModel(getPastSessions)
    }

    @Test
    fun setsPastSessions() {
        val pastSessions = viewModel.pastSessions.value
        assertThat(pastSessions).isEqualTo(dummyPastSessions)
    }
}