package cl.alexissilva.trainerapp.ui.history

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.domain.Session
import cl.alexissilva.trainerapp.domain.SessionStatus
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

    private val dummyPastSessions = listOf(
        Session("1", "session1", status = SessionStatus.DONE),
        Session("2", "session2", status = SessionStatus.SKIPPED),
        Session("3", "session3", status = SessionStatus.DONE),
    )
    private lateinit var viewModel: HistoryViewModel

    @Before
    fun setUp() {
        viewModel = mock {
            on { pastSessions } doReturn MutableStateFlow(dummyPastSessions)
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
    fun showsPastSessionList() {
        launchFragmentInContainer { HistoryFragment(viewModel) }
        for (pastSession in dummyPastSessions.subList(0,2)) {
            onView(withText(pastSession.name)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun showsNoPastWorkoutText_whenNoPastWorkouts() {
        val emptyFlow = MutableStateFlow(emptyList<Session>())
        whenever(viewModel.pastSessions).thenReturn(emptyFlow)
        launchFragmentInContainer { HistoryFragment(viewModel) }
        onView(withId(R.id.noPastSessions_textView)).check(matches(isDisplayed()))
    }


}