package cl.alexissilva.trainerapp.ui.history

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.domain.Workout
import cl.alexissilva.trainerapp.domain.WorkoutStatus
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.robolectric.annotation.Config

@Config(instrumentedPackages = ["androidx.loader.content"])
@RunWith(AndroidJUnit4::class)
class HistoryFragmentTest {

    private val pastWorkouts = listOf(
        Workout("1", "workout1", status = WorkoutStatus.DONE),
        Workout("2", "workout2", status = WorkoutStatus.SKIPPED),
        Workout("3", "workout3", status = WorkoutStatus.DONE),
    )
    private lateinit var viewModel: HistoryViewModel

    @Before
    fun setUp() {
        viewModel = mock {
            on { pastWorkouts } doReturn MutableStateFlow(pastWorkouts)
        }
    }

    @Test
    fun countsDoneWorkouts() {
        launchFragmentInContainer { HistoryFragment(viewModel) }
        onView(withId(R.id.done_textView)).check(matches(withText("2")))
    }

    @Test
    fun countsSkippedWorkouts() {
        launchFragmentInContainer { HistoryFragment(viewModel) }
        onView(withId(R.id.skipped_textView)).check(matches(withText("1")))
    }

    @Test
    fun showsPastWorkoutList() {
        launchFragmentInContainer { HistoryFragment(viewModel) }
        for (pastWorkout in pastWorkouts.subList(0,2)) {
            onView(withText(pastWorkout.name)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun showsNoPastWorkoutText_whenNoPastWorkouts() {
        val emptyFlow = MutableStateFlow(emptyList<Workout>())
        whenever(viewModel.pastWorkouts).thenReturn(emptyFlow)
        launchFragmentInContainer { HistoryFragment(viewModel) }
        onView(withId(R.id.noPastWorkouts_textView)).check(matches(isDisplayed()))
    }


}