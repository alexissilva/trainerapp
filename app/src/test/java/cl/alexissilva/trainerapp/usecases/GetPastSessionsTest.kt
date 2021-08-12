package cl.alexissilva.trainerapp.usecases

import cl.alexissilva.trainerapp.data.SessionRepository
import cl.alexissilva.trainerapp.domain.Session
import cl.alexissilva.trainerapp.domain.SessionStatus
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub


class GetPastSessionsTest {

    private lateinit var repository: SessionRepository
    private lateinit var getPastSessions: GetPastSessions

    @Before
    fun setUp() {
        repository = mock()
        getPastSessions = GetPastSessions(repository)
    }

    @Test
    fun invoke_getsPastSessions() = runBlocking {
        val localSessions = listOf(
            Session("1", "s1", status = SessionStatus.SKIPPED),
            Session("2", "s2", status = SessionStatus.DONE),
            Session("3", "s3", status = SessionStatus.PENDING),
            Session("4", "s4", status = SessionStatus.PENDING),
        )

        repository.stub {
            on { getLocalSessions() } doReturn flowOf(localSessions)
        }

        val pastSessions = getPastSessions().first()
        assertThat(pastSessions).isEqualTo(localSessions.subList(0, 2))

    }
}