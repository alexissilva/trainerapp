package cl.alexissilva.trainerapp.ui.sessions

import cl.alexissilva.trainerapp.domain.Session
import cl.alexissilva.trainerapp.usecases.GetUpcomingSessions
import cl.alexissilva.trainerapp.utils.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock


@ExperimentalCoroutinesApi
class SessionsViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val dummySessions = listOf(
        Session("1", "s1"),
        Session("2", "s2"),
    )

    private lateinit var getUpcomingSessions: GetUpcomingSessions
    private lateinit var viewModel: SessionsViewModel

    @Before
    fun setUp() {

        getUpcomingSessions = mock {
            on { invoke() } doReturn flowOf(dummySessions)
        }
        viewModel = SessionsViewModel(getUpcomingSessions)
    }

    @Test
    fun getUpcomingSessions() = runBlockingTest {
        val viewModelSession = viewModel.sessions.first()
        assertThat(viewModelSession).isEqualTo(dummySessions)
    }
}