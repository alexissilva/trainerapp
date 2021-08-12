package cl.alexissilva.trainerapp.framework.network

import androidx.test.filters.SmallTest
import cl.alexissilva.trainerapp.data.RemoteResult
import cl.alexissilva.trainerapp.domain.Session
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import java.time.LocalDate

@ExperimentalCoroutinesApi
@SmallTest
@RunWith(MockitoJUnitRunner::class)
class NetworkSessionSourceTest {

    private val dummySession = Session("dummy", "dummy", LocalDate.of(2020, 1, 1))

    private lateinit var networkSessionSource: NetworkSessionSource

    @Mock
    private lateinit var sessionApi: SessionsApi

    @Before
    fun setUp() {
        networkSessionSource = NetworkSessionSource(sessionApi)
    }

    @Test
    fun getSessionsReturnsARemoteSuccess() = runBlockingTest {
        val body = SessionsResponse(listOf(dummySession))
        val response = Response.success(body)
        `when`(sessionApi.getSessions()).thenReturn(response)

        val result = networkSessionSource.getSessions()
        assertThat(result).isInstanceOf(RemoteResult.Success::class.java)
    }

    @Test
    fun getSessionsReturnsARemoteError() = runBlockingTest {
        val errorBody = ResponseBody.create(null, "")
        val response = Response.error<SessionsResponse>(500, errorBody)
        `when`(sessionApi.getSessions()).thenReturn(response)

        val result = networkSessionSource.getSessions()
        assertThat(result).isInstanceOf(RemoteResult.Error::class.java)
    }
}