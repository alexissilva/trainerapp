package cl.alexissilva.trainerapp.data

import androidx.test.filters.SmallTest
import cl.alexissilva.trainerapp.domain.Session
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@SmallTest
class SessionRepositoryImplTest {
    private val dummySession = Session("dummy", "dummy")

    private lateinit var localSource: LocalSessionSource
    private lateinit var remoteSource: RemoteSessionSource
    private lateinit var sessionRepository: SessionRepositoryImpl

    @Before
    fun setUp() {
        localSource = mock()
        remoteSource = mock()
        sessionRepository = SessionRepositoryImpl(
            localSource,
            remoteSource
        )
    }

    @Test
    fun getsLocalSessions() {
        val localSessions = flowOf(listOf(dummySession))
        whenever(localSource.getAll()).thenReturn(localSessions)
        val repositorySession = sessionRepository.getLocalSessions()
        assertThat(repositorySession).isEqualTo(localSessions)
    }

    @Test
    fun getsSessionById() {
        val sessionFlow = flowOf(dummySession)
        whenever(localSource.getSessionById(any())).thenReturn(sessionFlow)
        val repositorySession = sessionRepository.getSessionById("id")
        assertThat(repositorySession).isEqualTo(sessionFlow)
    }

    @Test
    fun getsRemoteSessions() = runBlockingTest {
        val remoteSessions = RemoteResult.Success(listOf(dummySession))
        whenever(remoteSource.getSessions()).thenReturn(remoteSessions)
        val repositorySession = sessionRepository.getRemoteSessions()
        assertThat(repositorySession).isEqualTo(remoteSessions)
    }

    @Test
    fun savesSessionLocally() = runBlockingTest {
        sessionRepository.saveSession(dummySession)
        verify(localSource).save(dummySession)
    }

    @Test
    fun savesSessionsLocally() = runBlockingTest {
        val sessions = listOf(dummySession)
        sessionRepository.saveSessions(sessions)
        verify(localSource).save(sessions)
    }

    @Test
    fun deletesLocalSession() = runBlockingTest {
        sessionRepository.deleteSession(dummySession)
        verify(localSource).delete(dummySession)
    }
}