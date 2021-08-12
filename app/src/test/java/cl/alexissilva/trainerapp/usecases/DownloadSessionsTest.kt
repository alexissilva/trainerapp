package cl.alexissilva.trainerapp.usecases

import cl.alexissilva.trainerapp.data.RemoteResult
import cl.alexissilva.trainerapp.data.SessionRepository
import cl.alexissilva.trainerapp.domain.Session
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub
import org.mockito.kotlin.verify

class DownloadSessionsTest {

    private lateinit var downloadSessions: DownloadSessions

    private lateinit var repository: SessionRepository

    @Before
    fun setUp() {
        repository = mock()
        downloadSessions = DownloadSessions(repository)
    }

    @Test
    fun invoke_getRemoteAndSaveSessions() = runBlocking {
        val sessionList = listOf(Session("1", "session"))
        val successResult = RemoteResult.Success(sessionList)
        repository.stub {
            onBlocking { getRemoteSessions() } doReturn successResult
        }
        assertThat(downloadSessions()).isEqualTo(successResult)
        verify(repository).saveSessions(sessionList)
    }

    @Test
    fun invoke_returnsError() = runBlocking {
        val errorResult = RemoteResult.Error<List<Session>>("error")
        repository.stub {
            onBlocking { getRemoteSessions() } doReturn errorResult
        }
        assertThat(downloadSessions()).isEqualTo(errorResult)
    }
}
