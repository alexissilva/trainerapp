package cl.alexissilva.trainerapp.usecases

import androidx.test.filters.SmallTest
import cl.alexissilva.trainerapp.data.SessionRepository
import cl.alexissilva.trainerapp.domain.Session
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub


@ExperimentalCoroutinesApi
@SmallTest
class GetLocalSessionTest {

    private lateinit var getLocalSession: GetLocalSession

    private lateinit var repository: SessionRepository

    @Before
    fun setUp() {
        repository = mock()
        getLocalSession = GetLocalSession(repository)
    }

    @Test
    fun invoke_getSessionById() = runBlockingTest {
        val dummySession = Session("1", "s1")
        repository.stub {
            on { getSessionById(any()) } doReturn flowOf(dummySession)
        }

        val session = getLocalSession("id").first()
        assertThat(session).isEqualTo(dummySession)
    }
}