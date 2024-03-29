package cl.alexissilva.trainerapp.ui.createworkoutlog

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.core.domain.ExerciseLog
import cl.alexissilva.trainerapp.core.testutils.DummyData
import cl.alexissilva.trainerapp.ui.adapters.exerciselogs.ExerciseLogsAdapter
import cl.alexissilva.trainerapp.ui.base.ActivityWithViewModelTesting.Companion.IS_BEING_TESTED_PARAM
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.flow.MutableStateFlow
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
class CreateWorkoutLogActivityTest {

    private val workoutId = "workoutIdArg"
    private val workoutLog = DummyData.workoutLog.copy(workoutId = workoutId)
    private val adapterExerciseLogs = emptyList<ExerciseLog>()
    private val testingIntent = Intent(ApplicationProvider.getApplicationContext(), CreateWorkoutLogActivity::class.java)
        .apply {
            putExtra(IS_BEING_TESTED_PARAM, true)
            putExtra("workoutId", workoutId)
        }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    lateinit var adapter: ExerciseLogsAdapter

    private lateinit var viewModel: CreateWorkoutLogViewModel
    private var scenario: ActivityScenario<CreateWorkoutLogActivity>? = null

    @Before
    fun setUp() {
        viewModel = mock {
            on { workoutLog } doReturn MutableStateFlow(workoutLog)
        }
        adapter = mock {
            on { getExerciseLogs() } doReturn adapterExerciseLogs
        }
        scenario = launchActivity(testingIntent)
        scenario?.onActivity {
            it.setupViewModel(viewModel)
        }
    }

    @After
    fun tearDown() {
        scenario?.close()
    }

    @Test
    fun createDraftedWorkoutLog_onViewModelCreated_withWorkoutIdArg() {
        verify(viewModel).createCompleteDraftWorkoutLog(workoutId)
    }

    @Test
    fun updateWorkoutLogWithAdapterInfo_onPressSaveButton() {
        onView(withId(R.id.saveButton)).perform(click())
        verify(viewModel).updateWorkoutLog(adapterExerciseLogs)
    }

}