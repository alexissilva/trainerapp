package cl.alexissilva.trainerapp.framework.network

import androidx.test.filters.SmallTest
import cl.alexissilva.trainerapp.data.RemoteResult
import cl.alexissilva.trainerapp.testutils.DummyData
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import retrofit2.Response

@ExperimentalCoroutinesApi
@SmallTest
@RunWith(MockitoJUnitRunner::class)
class NetworkWorkoutSourceTest {

    private val workout = DummyData.workout

    private lateinit var networkWorkoutSource: NetworkWorkoutSource

    @Mock
    private lateinit var workoutApi: WorkoutApi

    @Before
    fun setUp() {
        networkWorkoutSource = NetworkWorkoutSource(workoutApi)
    }

    @Test
    fun getWorkoutsReturnsARemoteSuccess() = runBlockingTest {
        val body = WorkoutsResponse(listOf(workout))
        val response = Response.success(body)
        whenever(workoutApi.getWorkouts()).thenReturn(response)

        val result = networkWorkoutSource.getWorkouts()
        assertThat(result).isInstanceOf(RemoteResult.Success::class.java)
    }

    @Test
    fun getWorkoutsReturnsARemoteError() = runBlockingTest {
        val errorBody = ResponseBody.create(null, "")
        val response = Response.error<WorkoutsResponse>(500, errorBody)
        whenever(workoutApi.getWorkouts()).thenReturn(response)

        val result = networkWorkoutSource.getWorkouts()
        assertThat(result).isInstanceOf(RemoteResult.Error::class.java)
    }
}