package cl.alexissilva.trainerapp.usecases

import androidx.test.filters.SmallTest
import cl.alexissilva.trainerapp.data.WorkoutRepository
import cl.alexissilva.trainerapp.domain.Workout
import cl.alexissilva.trainerapp.domain.WorkoutStatus
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
class GetUpcomingWorkoutsTest {

    private val fakeCurrentDate = LocalDate.of(2021, 1, 1)

    private lateinit var getUpcomingWorkouts: GetUpcomingWorkouts

    private lateinit var repository: WorkoutRepository

    @Before
    fun setUp() {
        val fixedClock = Clock.fixed(
            fakeCurrentDate.atStartOfDay(ZoneId.systemDefault()).toInstant(),
            ZoneId.systemDefault()
        )
        repository = mock()
        getUpcomingWorkouts = GetUpcomingWorkouts(repository, fixedClock)

    }

    @Test
    fun invoke_getUpcomingWorkouts() = runBlockingTest {
        val localWorkouts = listOf(
            Workout("1", "s1", LocalDate.of(2020, 1, 1), emptyList(), WorkoutStatus.DONE),
            Workout("2", "s2", LocalDate.of(2021, 2, 1), emptyList(), WorkoutStatus.DONE),
            Workout("3", "s3", LocalDate.of(2021, 3, 1), emptyList(), WorkoutStatus.PENDING),
        )

        repository.stub {
            on { getLocalWorkouts() } doReturn flowOf(localWorkouts)
        }

        val upcomingWorkouts = getUpcomingWorkouts().first()
        assertThat(upcomingWorkouts).isEqualTo(localWorkouts.subList(2, 3))
    }
}