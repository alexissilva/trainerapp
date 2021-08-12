package cl.alexissilva.trainerapp.ui.nextsession

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import cl.alexissilva.trainerapp.data.RemoteResult
import cl.alexissilva.trainerapp.domain.Session
import cl.alexissilva.trainerapp.domain.SessionStatus
import cl.alexissilva.trainerapp.usecases.DownloadSessions
import cl.alexissilva.trainerapp.usecases.GetNextSession
import cl.alexissilva.trainerapp.usecases.UpdateSessionStatus
import cl.alexissilva.trainerapp.utils.MainCoroutineRule
import cl.alexissilva.trainerapp.utils.TestCoroutineContextProvider
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NextSessionViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val contextProvider = TestCoroutineContextProvider()

    private lateinit var downloadSessions: DownloadSessions

    private lateinit var getNextSession: GetNextSession

    private lateinit var updateSessionStatus: UpdateSessionStatus


    private lateinit var viewModel: NextSessionViewModel

    private val dummySession = Session("dummy", "dummy")


    @Before
    fun setUp() {
        updateSessionStatus = mock()
        downloadSessions = mock()
        getNextSession = mock {
            on { invoke() } doReturn flowOf(null)
        }
        viewModel =
            NextSessionViewModel(downloadSessions, getNextSession, updateSessionStatus, contextProvider)
    }

    @Test
    fun onSuccessfulDownload_setErrorToNull() = runBlockingTest {
        val remoteSuccess = RemoteResult.Success(listOf(dummySession))
        whenever(downloadSessions()).thenReturn(remoteSuccess)

        viewModel.downloadSessions()

        verify(downloadSessions).invoke()
        assertThat(viewModel.errorMessage.value).isNull()
    }

    @Test
    fun onFailDownload_setErrorWithRemoteMessage() = runBlockingTest {
        val remoteSuccess = RemoteResult.Error<List<Session>>("remote-error")
        whenever(downloadSessions()).thenReturn(remoteSuccess)

        viewModel.downloadSessions()

        verify(downloadSessions).invoke()
        assertThat(viewModel.errorMessage.value).isEqualTo("remote-error")
    }

    @Test
    fun setSession_withNextSession() {
        whenever(getNextSession()).thenReturn(flowOf(dummySession))

        val viewModel = NextSessionViewModel(downloadSessions, getNextSession, updateSessionStatus, contextProvider)
        val viewModelSessions = viewModel.session.value
        assertThat(viewModelSessions).isEqualTo(dummySession)
    }

    @Test
    fun updateStatus_ofNextSession() = runBlockingTest {
        whenever(getNextSession()).thenReturn(flowOf(dummySession))
        val viewModel = NextSessionViewModel(downloadSessions, getNextSession, updateSessionStatus, contextProvider)


        viewModel.updateSessionStatus(SessionStatus.DONE)
        verify(updateSessionStatus).invoke(dummySession, SessionStatus.DONE)
    }

}