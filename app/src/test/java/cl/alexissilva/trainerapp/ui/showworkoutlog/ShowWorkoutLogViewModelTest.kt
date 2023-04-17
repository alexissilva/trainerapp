package cl.alexissilva.trainerapp.ui.showworkoutlog

import cl.alexissilva.trainerapp.core.testutils.DummyData
import cl.alexissilva.trainerapp.core.usecases.GetWorkoutLog
import cl.alexissilva.trainerapp.testutils.MainCoroutineRule
import cl.alexissilva.trainerapp.testutils.TestCoroutineContextProvider
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class ShowWorkoutLogViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    private val contextProvider = TestCoroutineContextProvider()

    private val workoutLog = DummyData.workoutLog
    private lateinit var viewModel: ShowWorkoutLogViewModel
    private lateinit var getWorkoutLog: GetWorkoutLog

    @Before
    fun setUp() {
        getWorkoutLog = mock {
            on { invoke(workoutLog.workoutId) } doReturn flowOf(workoutLog)
        }
        viewModel = ShowWorkoutLogViewModel(contextProvider, getWorkoutLog)
    }

    @Test
    fun onLoadWorkoutLog_setWorkoutLog() {
        viewModel.loadWorkoutLog(workoutLog.workoutId)
        val viewModelWorkoutLog = viewModel.workoutLog.value
        assertThat(viewModelWorkoutLog).isEqualTo(workoutLog)
    }

}