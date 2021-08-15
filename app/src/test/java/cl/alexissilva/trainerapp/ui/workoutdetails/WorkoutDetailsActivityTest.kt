package cl.alexissilva.trainerapp.ui.workoutdetails

import android.content.Intent
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.domain.Workout
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.robolectric.annotation.Config
import java.time.LocalDate

@Config(instrumentedPackages = ["androidx.loader.content"])
@RunWith(AndroidJUnit4::class)
class WorkoutDetailsActivityTest {

    private val workout = Workout("1", "dummy name", LocalDate.of(2020, 1, 1))

    private val testingIntent = Intent(getApplicationContext(), WorkoutDetailsActivity::class.java)
        .apply { putExtra("isBeingTested", true) }

    @get:Rule
    var activityScenarioRule = activityScenarioRule<WorkoutDetailsActivity>(testingIntent)

    private lateinit var viewModel: WorkoutDetailsViewModel

    @Before
    fun setUp() {
        viewModel = mock {
            on { workout } doReturn MutableStateFlow(workout)
        }
    }

    @Test
    fun showWorkoutData() {
        activityScenarioRule.scenario.onActivity {
            it.setupViewModel(viewModel)
        }
        onView(withId(R.id.date_textView)).check(matches(withText("Wednesday, 1 January")))
    }
}