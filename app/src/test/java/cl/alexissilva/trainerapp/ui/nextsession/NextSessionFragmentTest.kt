package cl.alexissilva.trainerapp.ui.nextsession

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.domain.Exercise
import cl.alexissilva.trainerapp.domain.GroupSet
import cl.alexissilva.trainerapp.domain.Session
import cl.alexissilva.trainerapp.domain.SessionStatus
import cl.alexissilva.trainerapp.utils.SwipeRefreshLayoutMatchers.isRefreshing
import cl.alexissilva.trainerapp.utils.simulateSwipeToRefresh
import kotlinx.coroutines.flow.MutableStateFlow
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.robolectric.annotation.Config
import java.time.LocalDate

@Config(instrumentedPackages = ["androidx.loader.content"])
@RunWith(AndroidJUnit4::class)
class NextSessionFragmentTest {

    private val dummyExercises = listOf(
        Exercise("Exercise1", groupSets = listOf(GroupSet(3, 10))),
        Exercise("Exercise2", groupSets = listOf(GroupSet(3, 10))),
    )
    private val dummySession = Session("1", "Session", LocalDate.of(2020, 1, 1), dummyExercises)
    private lateinit var viewModel: NextSessionViewModel

    @Before
    fun setUp() {
        viewModel = mock {
            on { session } doReturn MutableStateFlow(dummySession)
            on { errorMessage } doReturn MutableStateFlow(null)
            on { isLoading } doReturn MutableStateFlow(false)
        }
    }

    private fun launchFragment() =
        launchFragmentInContainer(themeResId = R.style.Theme_TrainerApp) {
            NextSessionFragment(viewModel)
        }

    @Test
    fun updatesSessionStatus_onPressDone() {
        launchFragment()
        onView(withId(R.id.done_button)).perform(click())
        verify(viewModel).updateSessionStatus(SessionStatus.DONE)
    }

    @Test
    fun updatesSessionStatus_onPressSkip() {
        launchFragment()
        onView(withId(R.id.skip_button)).perform(click())
        verify(viewModel).updateSessionStatus(SessionStatus.SKIPPED)
    }

    @Test
    fun downloadsSessions_onSwipeDown() {
        val scenario = launchFragment()
        scenario.onFragment {
            it.simulateSwipeToRefresh(R.id.swipeRefreshLayout)
        }
        verify(viewModel).downloadSessions()
    }

    @Test
    fun showsSessionAndExercisesName() {
        launchFragment()
        onView(withId(R.id.sessionName_textView)).check(matches(withText(dummySession.name)))
        for (exercise in dummyExercises) {
            onView(withText(exercise.name)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun formatsSessionDate() {
        launchFragment()
        onView(withId(R.id.date_textView)).check(matches(withText("Wednesday, 1 January")))
    }

    @Test
    fun countsExercisesSetsAndReps() {
        launchFragment()
        onView(withId(R.id.exercises_textView)).check(matches(withText("2")))
        onView(withId(R.id.sets_textView)).check(matches(withText("6")))
        onView(withId(R.id.reps_textView)).check(matches(withText("60")))
    }

    @Test
    fun showsErrorSnackbar_whenErrorMessageIsNotNull() {
        whenever(viewModel.errorMessage).thenReturn(MutableStateFlow("view_model_error"))
        launchFragment()
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText("view_model_error")))
    }

    @Test
    fun showsProgressBar_whenIsLoading() {
        whenever(viewModel.isLoading).thenReturn(MutableStateFlow(true))
        launchFragment()
        onView(withId(R.id.swipeRefreshLayout)).check(matches(isRefreshing()))
    }

    @Test
    fun hidesProgressBar_whenEndLoading() {
        val isLoadingFlow = MutableStateFlow(true)
        whenever(viewModel.isLoading).thenReturn(isLoadingFlow)
        launchFragment()

        onView(withId(R.id.swipeRefreshLayout)).check(matches(isRefreshing()))
        isLoadingFlow.value = false
        onView(withId(R.id.swipeRefreshLayout)).check(matches(not(isRefreshing())))
    }


    @Test
    fun showsNoSessionTextAndHidesNextSessionLayout_whenSessionIsNull() {
        whenever(viewModel.session).thenReturn(MutableStateFlow(null))
        launchFragment()
        onView(withId(R.id.noSession_textView)).check(matches(isDisplayed()))
        onView(withId(R.id.nextSession_layout)).check(matches(not(isDisplayed())))
    }

}