package cl.alexissilva.trainerapp.core.usecases

import cl.alexissilva.trainerapp.core.data.RemoteResult
import cl.alexissilva.trainerapp.core.data.WorkoutRepository
import cl.alexissilva.trainerapp.core.domain.Workout
import cl.alexissilva.trainerapp.core.usecases.DownloadWorkouts
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub
import org.mockito.kotlin.verify

class DownloadWorkoutsTest {

    private lateinit var downloadWorkouts: DownloadWorkouts

    private lateinit var repository: WorkoutRepository

    @Before
    fun setUp() {
        repository = mock()
        downloadWorkouts = DownloadWorkouts(repository)
    }

    @Test
    fun invoke_getRemoteAndSaveWorkouts() = runBlocking {
        val workoutList = listOf(Workout("1", "workout"))
        val successResult = RemoteResult.Success(workoutList)
        repository.stub {
            onBlocking { getRemoteWorkouts() } doReturn successResult
        }
        assertThat(downloadWorkouts()).isEqualTo(successResult)
        verify(repository).saveWorkouts(workoutList)
    }

    @Test
    fun invoke_returnsError() = runBlocking {
        val errorResult = RemoteResult.Error<List<Workout>>("error")
        repository.stub {
            onBlocking { getRemoteWorkouts() } doReturn errorResult
        }
        assertThat(downloadWorkouts()).isEqualTo(errorResult)
    }
}
