package cl.alexissilva.trainerapp.usecases

import cl.alexissilva.trainerapp.data.SessionRepository
import cl.alexissilva.trainerapp.domain.Session
import cl.alexissilva.trainerapp.domain.SessionStatus
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class UpdateSessionStatusTest {


    private lateinit var updateSessionStatus: UpdateSessionStatus

    private lateinit var repository: SessionRepository

    @Before
    fun setUp() {
        repository = mock()
        updateSessionStatus = UpdateSessionStatus(repository)
    }

    @Test
    fun invoke_updateStatusSession() = runBlocking {
        val pendingSession = Session("1", "s1", status = SessionStatus.PENDING)
        updateSessionStatus(pendingSession, SessionStatus.DONE)

        val expectedSession = Session("1", "s1", status = SessionStatus.DONE)
        verify(repository).saveSession(expectedSession)
    }
}