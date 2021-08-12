package cl.alexissilva.trainerapp.framework.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import cl.alexissilva.trainerapp.domain.Session
import cl.alexissilva.trainerapp.domain.SessionStatus
import cl.alexissilva.trainerapp.utils.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import java.time.LocalDate

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
@SmallTest
class DatabaseSessionSourceTest {

    private val dummySession = Session("dummyId", "dummyName")
    private val dummySessionEntity =
        SessionEntity("dummyId", "dummyName", LocalDate.MIN, emptyList(), SessionStatus.PENDING)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var sessionDao: SessionDao

    private lateinit var databaseSource: DatabaseSessionSource


    @Before
    fun setUp() {
        val sessionMap = SessionMap() //FIXME should be mocked(?)
        databaseSource = DatabaseSessionSource(sessionDao, sessionMap)
    }

    @Test
    fun insertsSessionEntity() = runBlockingTest {
        databaseSource.save(dummySession)
        verify(sessionDao).insert(dummySessionEntity)
    }

    @Test
    fun deletesSessionEntity() = runBlockingTest {
        databaseSource.delete(dummySession)
        verify(sessionDao).delete(dummySessionEntity)
    }

    @Test
    fun getsAllSessions() = runBlockingTest {
        val sessionEntities = flowOf((listOf(dummySessionEntity)))
        whenever(sessionDao.getAll()).thenReturn(sessionEntities)

        val sessions = databaseSource.getAll().toList()[0]
        assertThat(sessions).containsExactly(dummySession)
    }

    @Test
    fun getsSessionById() = runBlockingTest {
        val sessionEntity = flowOf(dummySessionEntity)
        whenever(sessionDao.getSessionById(any())).thenReturn(sessionEntity)

        val session = databaseSource.getSessionById("id").first()
        assertThat(session).isEqualTo(dummySession)
    }

    @Test
    fun getsNullWhenNotFoundSession() = runBlockingTest {
        whenever(sessionDao.getSessionById(any())).thenReturn(flowOf(null))

        val session = databaseSource.getSessionById("id").first()
        assertThat(session).isNull()
    }
}