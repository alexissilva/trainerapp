package cl.alexissilva.trainerapp.usecases

import androidx.test.filters.SmallTest
import cl.alexissilva.trainerapp.data.SessionRepository
import cl.alexissilva.trainerapp.domain.Session
import cl.alexissilva.trainerapp.domain.SessionStatus
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub
import java.time.Clock
import java.time.LocalDate
import java.time.ZoneId

@ExperimentalCoroutinesApi
@SmallTest
class GetNextSessionTest {

    private val fakeCurrentDate = LocalDate.of(2021, 1, 1)

    private lateinit var getNextSession: GetNextSession

    private lateinit var repository: SessionRepository

    @Before
    fun setUp() {
        val fixedClock = Clock.fixed(
            fakeCurrentDate.atStartOfDay(ZoneId.systemDefault()).toInstant(),
            ZoneId.systemDefault()
        )
        repository = mock()
        getNextSession = GetNextSession(repository, fixedClock)

    }

    @Test
    fun invoke_getNextSession() = runBlockingTest {
        val localSessions = listOf(
            Session("1", "s1", LocalDate.of(2020, 1, 1), emptyList(), SessionStatus.SKIPPED),
            Session("2", "s2", LocalDate.of(2021, 2, 1), emptyList(), SessionStatus.DONE),
            Session("3", "s3", LocalDate.of(2021, 3, 1), emptyList(), SessionStatus.PENDING),

        )

        repository.stub {
            on { getLocalSessions() } doReturn flowOf(localSessions)
        }

        val nextSession = getNextSession().first()
        assertThat(nextSession).isEqualTo(localSessions[2])
    }
}