package cl.alexissilva.trainerapp.ui.nextworkout

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.domain.Exercise
import cl.alexissilva.trainerapp.domain.GroupSet
import cl.alexissilva.trainerapp.domain.Workout
import cl.alexissilva.trainerapp.domain.WorkoutStatus
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
class NextWorkoutFragmentTest {

    private val exercises = listOf(
        Exercise("Exercise1", groupSets = listOf(GroupSet(3, 10))),
        Exercise("Exercise2", groupSets = listOf(GroupSet(3, 10))),
    )
    private val workout = Workout("1", "Workout", LocalDate.of(2020, 1, 1), exercises)
    private lateinit var viewModel: NextWorkoutViewModel

    @Before
    fun setUp() {
        viewModel = mock {
            on { workout } doReturn MutableStateFlow(workout)
            on { errorMessage } doReturn MutableStateFlow(null)
            on { isLoading } doReturn MutableStateFlow(false)
        }
    }

    private fun launchFragment() =
        launchFragmentInContainer(themeResId = R.style.Theme_TrainerApp) {
            NextWorkoutFragment(viewModel)
        }

    @Test
    fun updatesWorkoutStatus_onPressDone() {
        launchFragment()
        onView(withId(R.id.done_button)).perform(click())
        verify(viewModel).updateWorkoutStatus(WorkoutStatus.DONE)
    }

    @Test
    fun updatesWorkoutStatus_onPressSkip() {
        launchFragment()
        onView(withId(R.id.skip_button)).perform(click())
        verify(viewModel).updateWorkoutStatus(WorkoutStatus.SKIPPED)
    }

    @Test
    fun downloadsWorkouts_onSwipeDown() {
        val scenario = launchFragment()
        scenario.onFragment {
            it.simulateSwipeToRefresh(R.id.swipeRefreshLayout)
        }
        verify(viewModel).downloadWorkouts()
    }

    @Test
    fun showsWorkoutAndExercisesName() {
        launchFragment()
        onView(withId(R.id.workoutName_textView)).check(matches(withText(workout.name)))
        for (exercise in exercises) {
            onView(withText(exercise.name)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun formatsWorkoutDate() {
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
    fun showsNoWorkoutTextAndHidesNextWorkoutLayout_whenWorkoutIsNull() {
        whenever(viewModel.workout).thenReturn(MutableStateFlow(null))
        launchFragment()
        onView(withId(R.id.noWorkout_textView)).check(matches(isDisplayed()))
        onView(withId(R.id.nextWorkout_layout)).check(matches(not(isDisplayed())))
    }

}