package cl.alexissilva.trainerapp.framework.network

import androidx.test.filters.SmallTest
import cl.alexissilva.trainerapp.data.RemoteResult
import cl.alexissilva.trainerapp.domain.Workout
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
class NetworkWorkoutSourceTest {

    private val workout = Workout("dummy", "dummy", LocalDate.of(2020, 1, 1))

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
        `when`(workoutApi.getWorkouts()).thenReturn(response)

        val result = networkWorkoutSource.getWorkouts()
        assertThat(result).isInstanceOf(RemoteResult.Success::class.java)
    }

    @Test
    fun getWorkoutsReturnsARemoteError() = runBlockingTest {
        val errorBody = ResponseBody.create(null, "")
        val response = Response.error<WorkoutsResponse>(500, errorBody)
        `when`(workoutApi.getWorkouts()).thenReturn(response)

        val result = networkWorkoutSource.getWorkouts()
        assertThat(result).isInstanceOf(RemoteResult.Error::class.java)
    }
}