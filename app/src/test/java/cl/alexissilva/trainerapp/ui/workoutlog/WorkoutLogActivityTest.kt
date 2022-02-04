package cl.alexissilva.trainerapp.ui.workoutlog

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.core.domain.ExerciseLog
import cl.alexissilva.trainerapp.core.domain.WorkoutStatus
import cl.alexissilva.trainerapp.core.testutils.DummyData
import cl.alexissilva.trainerapp.ui.adapters.exerciselogs.ExerciseLogsAdapter
import cl.alexissilva.trainerapp.ui.base.ActivityWithViewModelTesting.Companion.IS_BEING_TESTED_PARAM
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.flow.MutableStateFlow
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.robolectric.annotation.Config

@Config(
    instrumentedPackages = ["androidx.loader.content"],
    application = HiltTestApplication::class
)
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class WorkoutLogActivityTest {

    private val workoutId = "workoutId"
    private val workoutLog = DummyData.workoutLog.copy(workoutId = workoutId)
    private val adapterExerciseLogs = emptyList<ExerciseLog>()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    lateinit var adapter: ExerciseLogsAdapter

    private lateinit var viewModel: WorkoutLogViewModel
    private var scenario: ActivityScenario<WorkoutLogActivity>? = null

    @Before
    fun setUp() {
        viewModel = mock {
            on { workoutLog } doReturn MutableStateFlow(workoutLog)
        }
        adapter = mock {
            on { getExerciseLogs() } doReturn adapterExerciseLogs
        }
    }

    @After
    fun tearDown() {
        scenario?.close()
    }

    private fun launchActivityAndSetupViewModel(
        workoutId: String? = null,
        workoutLogId: String? = null
    ) {
        scenario = launchActivity(intent(workoutId, workoutLogId))
        scenario?.onActivity {
            it.setupViewModel(viewModel)
        }
    }

    private fun intent(workoutId: String?, workoutLogId: String?): Intent {
        return Intent(ApplicationProvider.getApplicationContext(), WorkoutLogActivity::class.java)
            .apply {
                putExtra(IS_BEING_TESTED_PARAM, true)
                workoutId?.let { putExtra("workoutId", it) }
                workoutLogId?.let { putExtra("workoutLogId", it) }
            }
    }

    @Test
    fun setupReadOnlyAdapter_whenReceivedLogId() {
        launchActivityAndSetupViewModel(workoutLogId = workoutLog.id)
        verify(adapter).setExerciseLogs(workoutLog.exerciseLogs, true)
    }

    @Test
    fun setupEditableAdapter_whenReceivedWorkoutId() {
        launchActivityAndSetupViewModel(workoutId = workoutId)
        verify(adapter).setExerciseLogs(workoutLog.exerciseLogs, false)
    }

    @Test
    fun setLoadedWorkoutLog_whenReceiveLogId() {
        launchActivityAndSetupViewModel(workoutLogId = workoutLog.id)
        verify(viewModel).loadWorkoutLog(workoutLog.id)
    }

    @Test
    fun setDraftedWorkoutLog_whenReceiveWorkoutId() {
        launchActivityAndSetupViewModel(workoutId = workoutId)
        verify(viewModel).createCompleteDraftWorkoutLog(workoutId)
    }

    @Test
    fun updateWorkoutLogWithAdapterInfo_onPressSaveButton() {
        launchActivityAndSetupViewModel(workoutId = workoutId)
        onView(withId(R.id.saveButton)).perform(click())
        verify(viewModel).updateWorkoutLog(adapterExerciseLogs)
    }


    @Test
    fun hideSaveButton_whenReceiveLogId() {
        launchActivityAndSetupViewModel(workoutLogId = workoutLog.id)
        onView(withId(R.id.saveButton)).check(matches(not(isDisplayed())))
    }

    @Test
    fun showSkippedText_whenWorkoutWasSkipped() {
        val skippedWorkout = workoutLog.copy(status = WorkoutStatus.SKIPPED)
        whenever(viewModel.workoutLog).thenReturn(MutableStateFlow(skippedWorkout))

        launchActivityAndSetupViewModel(workoutLogId = skippedWorkout.id)
        onView(withId(R.id.workoutSkipped_textView)).check(matches(isDisplayed()))
    }


    @Test
    fun hideSkippedText_whenWorkoutWasDone() {
        val doneWorkout = workoutLog.copy(status = WorkoutStatus.DONE)
        whenever(viewModel.workoutLog).thenReturn(MutableStateFlow(doneWorkout))

        launchActivityAndSetupViewModel(workoutLogId = doneWorkout.id)
        onView(withId(R.id.workoutSkipped_textView)).check(matches(not(isDisplayed())))
    }
}