package cl.alexissilva.trainerapp.ui.workoutlog

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.core.testutils.DummyData
import cl.alexissilva.trainerapp.ui.adapters.exerciselogs.ExerciseLogsAdapter
import cl.alexissilva.trainerapp.ui.base.ActivityWithViewModelTesting.Companion.IS_BEING_TESTED_PARAM
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.robolectric.annotation.Config


@Config(
    instrumentedPackages = ["androidx.loader.content"],
    application = HiltTestApplication::class
)
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class WorkoutLogActivityTest {

    private val workoutId = "workoutId"
    private val draftWorkoutLog = DummyData.workoutLog.copy(workoutId = workoutId)

    private val intent =
        Intent(ApplicationProvider.getApplicationContext(), WorkoutLogActivity::class.java)
            .apply {
                putExtra(IS_BEING_TESTED_PARAM, true)
                putExtra("workoutId", workoutId)
            }


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    lateinit var adapter: ExerciseLogsAdapter

    private lateinit var viewModel: WorkoutLogViewModel
    private lateinit var scenario: ActivityScenario<WorkoutLogActivity>


    @Before
    fun setUp() {
        viewModel = mock {
            onBlocking { createCompleteDraftWorkoutLog(draftWorkoutLog.workoutId) } doReturn draftWorkoutLog
        }
        adapter = mock()
        scenario = launchActivity(intent)
        scenario.onActivity {
            it.setupViewModel(viewModel)
        }
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun setupAdapterWithDraftWorkoutLog_whenReceiveWorkoutId() {
        verify(adapter).setExerciseLogs(draftWorkoutLog.exerciseLogs)
    }

    @Test
    fun saveLog_onPressSaveButton() {
        onView(withId(R.id.saveButton)).perform(click())
        verify(viewModel).saveWorkoutLog(draftWorkoutLog)
    }
}