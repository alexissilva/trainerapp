package cl.alexissilva.trainerapp.ui.sessiondetails

import androidx.lifecycle.SavedStateHandle
import androidx.test.filters.SmallTest
import cl.alexissilva.trainerapp.domain.Session
import cl.alexissilva.trainerapp.usecases.GetLocalSession
import cl.alexissilva.trainerapp.utils.MainCoroutineRule
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
class SessionDetailsViewModelTest {
    private val dummySession = Session("1", "s1")

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    private lateinit var viewModel: SessionDetailsViewModel

    @Before
    fun setUp() {
        val map = mutableMapOf(Pair("sessionId", "1")) as Map<String, *>
        val state = SavedStateHandle(map)
        val getLocalSession = mock<GetLocalSession> {
            on { invoke("1") } doReturn flowOf(dummySession)
        }
        viewModel = SessionDetailsViewModel(getLocalSession, state)
    }

    @Test
    fun getLocalSessionUsingState() = runBlockingTest {
        val session = viewModel.session.first()
        assertThat(session).isEqualTo(dummySession)
    }
}