package cl.alexissilva.trainerapp.usecases

import androidx.test.filters.SmallTest
import cl.alexissilva.trainerapp.data.WorkoutRepository
import cl.alexissilva.trainerapp.domain.Workout
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
class GetLocalWorkoutTest {

    private lateinit var getLocalWorkout: GetLocalWorkout

    private lateinit var repository: WorkoutRepository

    @Before
    fun setUp() {
        repository = mock()
        getLocalWorkout = GetLocalWorkout(repository)
    }

    @Test
    fun invoke_getWorkoutById() = runBlockingTest {
        val workout = Workout("1", "s1")
        repository.stub {
            on { getWorkoutById(any()) } doReturn flowOf(workout)
        }

        val localWorkout = getLocalWorkout("id").first()
        assertThat(localWorkout).isEqualTo(workout)
    }
}