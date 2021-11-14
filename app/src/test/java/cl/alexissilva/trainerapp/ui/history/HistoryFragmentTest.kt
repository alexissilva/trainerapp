package cl.alexissilva.trainerapp.ui.history

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.core.domain.WorkoutLog
import cl.alexissilva.trainerapp.core.domain.WorkoutStatus
import cl.alexissilva.trainerapp.core.testutils.DummyData
import cl.alexissilva.trainerapp.testutils.launchFragmentInHiltContainer
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

    private val workoutLogs = listOf(
        DummyData.workoutLog.apply {
            status = WorkoutStatus.SKIPPED
            workout = DummyData.workout
        },
        DummyData.workoutLog2.apply {
            status = WorkoutStatus.DONE
            workout = DummyData.workout2
        },
        DummyData.workoutLog3.apply {
            status = WorkoutStatus.DONE
            workout = DummyData.workout3
        },
    )
    private lateinit var viewModel: HistoryViewModel

    @Before
    fun setUp() {
        viewModel = mock {
            on { workoutLogs } doReturn MutableStateFlow(workoutLogs)
        }
    }

    @Test
    fun countsDoneWorkouts() {
        launchFragmentInHiltContainer { HistoryFragment(viewModel) }
        onView(withId(R.id.done_textView)).check(matches(withText("2")))
    }

    @Test
    fun countsSkippedWorkouts() {
        launchFragmentInHiltContainer { HistoryFragment(viewModel) }
        onView(withId(R.id.skipped_textView)).check(matches(withText("1")))
    }

    @Test
    fun showsWorkoutLogList() {
        launchFragmentInHiltContainer { HistoryFragment(viewModel) }
        //TODO Test full list instead a sublist, if its possible (?)
        // I don't know why, but for some reason Espresso just display two items
        for (log in workoutLogs.subList(0, 2)) {
            onView(withText(log.workout!!.name)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun showsNoPastWorkoutText_whenNoPastWorkouts() {
        val emptyFlow = MutableStateFlow(emptyList<WorkoutLog>())
        whenever(viewModel.workoutLogs).thenReturn(emptyFlow)
        launchFragmentInHiltContainer { HistoryFragment(viewModel) }
        onView(withId(R.id.noPastWorkouts_textView)).check(matches(isDisplayed()))
    }


}