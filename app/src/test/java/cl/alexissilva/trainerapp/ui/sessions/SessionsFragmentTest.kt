package cl.alexissilva.trainerapp.ui.sessions

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.domain.Session
import cl.alexissilva.trainerapp.ui.adapters.SessionsAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.robolectric.annotation.Config

@Config(instrumentedPackages = ["androidx.loader.content"])
@MediumTest
@RunWith(AndroidJUnit4::class)
class SessionsFragmentTest {

    private val dummySessions = listOf(
        Session("1", "session 1"),
        Session("1", "session 2"),
        Session("1", "session 3")
    )
    private lateinit var viewModel: SessionsViewModel

    @Before
    fun setUp() {
        viewModel = mock {
            on { sessions } doReturn MutableStateFlow(dummySessions)
        }
    }

    @Test
    fun showsSessionList() {
        launchFragmentInContainer { SessionsFragment(viewModel) }
        for (session in dummySessions) {
            onView(withText(session.name)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun showsNoSessionsText_whenSessionListIsEmpty() {
        val emptyFlow = MutableStateFlow(emptyList<Session>())
        whenever(viewModel.sessions).thenReturn(emptyFlow)
        launchFragmentInContainer { SessionsFragment(viewModel) }
        onView(withId(R.id.noSessions_textView)).check(matches(isDisplayed()))
    }

    @Test
    fun navigatesToSessionDetails_onItemClick() {
        val navController = mock<NavController>()
        val scenario = launchFragmentInContainer { SessionsFragment(viewModel) }
        scenario.onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
        }
        onView(withId(R.id.sessions_recyclerView)).perform(
            actionOnItemAtPosition<SessionsAdapter.ViewHolder>(1, click())
        )
        verify(navController).navigate(
            SessionsFragmentDirections.actionSessionsFragmentToSessionDetailsActivity("1")
        )
    }


}