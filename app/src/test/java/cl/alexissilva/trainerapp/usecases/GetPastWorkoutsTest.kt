package cl.alexissilva.trainerapp.usecases

import cl.alexissilva.trainerapp.data.WorkoutRepository
import cl.alexissilva.trainerapp.domain.Workout
import cl.alexissilva.trainerapp.domain.WorkoutStatus
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub


class GetPastWorkoutsTest {

    private lateinit var repository: WorkoutRepository
    private lateinit var getPastWorkouts: GetPastWorkouts

    @Before
    fun setUp() {
        repository = mock()
        getPastWorkouts = GetPastWorkouts(repository)
    }

    @Test
    fun invoke_getsPastWorkouts() = runBlocking {
        val localWorkouts = listOf(
            Workout("1", "s1", status = WorkoutStatus.SKIPPED),
            Workout("2", "s2", status = WorkoutStatus.DONE),
            Workout("3", "s3", status = WorkoutStatus.PENDING),
            Workout("4", "s4", status = WorkoutStatus.PENDING),
        )

        repository.stub {
            on { getLocalWorkouts() } doReturn flowOf(localWorkouts)
        }

        val pastWorkouts = getPastWorkouts().first()
        assertThat(pastWorkouts).isEqualTo(localWorkouts.subList(0, 2))

    }
}