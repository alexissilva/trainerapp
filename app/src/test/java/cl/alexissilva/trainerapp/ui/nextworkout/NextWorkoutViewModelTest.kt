package cl.alexissilva.trainerapp.ui.nextworkout

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import cl.alexissilva.trainerapp.core.data.RemoteResult
import cl.alexissilva.trainerapp.core.domain.Workout
import cl.alexissilva.trainerapp.core.testutils.DummyData
import cl.alexissilva.trainerapp.core.usecases.DownloadWorkouts
import cl.alexissilva.trainerapp.core.usecases.GetNextWorkout
import cl.alexissilva.trainerapp.core.usecases.SkipWorkout
import cl.alexissilva.trainerapp.testutils.MainCoroutineRule
import cl.alexissilva.trainerapp.testutils.TestCoroutineContextProvider
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class NextWorkoutViewModelTest {

    private val workout = DummyData.workout

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val contextProvider = TestCoroutineContextProvider()

    private lateinit var downloadWorkouts: DownloadWorkouts

    private lateinit var getNextWorkout: GetNextWorkout

    private lateinit var skipWorkout: SkipWorkout


    private lateinit var viewModel: NextWorkoutViewModel

    private lateinit var nextWorkoutFlow: Flow<Workout?>


    @Before
    fun setUp() {
        skipWorkout = mock()
        downloadWorkouts = mock()
        nextWorkoutFlow = flowOf<Workout?>(null)
        getNextWorkout = mock {
            on { invoke() } doReturn nextWorkoutFlow
        }
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = NextWorkoutViewModel(
            downloadWorkouts,
            mock(),
            getNextWorkout,
            skipWorkout,
            contextProvider
        )
    }

    @Test
    fun onSuccessfulDownload_setErrorToNull() = runTest {
        val remoteSuccess = RemoteResult.Success(listOf(workout))
        whenever(downloadWorkouts()).thenReturn(remoteSuccess)

        viewModel.syncWorkouts()

        verify(downloadWorkouts).invoke()
        assertThat(viewModel.errorMessage.value).isNull()
    }

    @Test
    fun onFailDownload_setErrorWithRemoteMessage() = runTest {
        val remoteSuccess = RemoteResult.Error<List<Workout>>("remote-error")
        whenever(downloadWorkouts()).thenReturn(remoteSuccess)

        viewModel.syncWorkouts()

        verify(downloadWorkouts).invoke()
        assertThat(viewModel.errorMessage.value).isEqualTo("remote-error")
    }

    @Test
    fun setWorkout_withNextWorkout() {
        whenever(nextWorkoutFlow).thenReturn(flowOf(workout))
        initViewModel()
        val viewModelWorkouts = viewModel.workout.value
        assertThat(viewModelWorkouts).isEqualTo(workout)
    }

    @Deprecated("Behavior deprecated. Delete this and create new tests.")
    @Test
    fun updateStatus_ofNextWorkout() = runTest {
        whenever(nextWorkoutFlow).thenReturn(flowOf(workout))
        initViewModel()
        viewModel.skipWorkout()
        verify(skipWorkout).invoke(workout)
    }

}