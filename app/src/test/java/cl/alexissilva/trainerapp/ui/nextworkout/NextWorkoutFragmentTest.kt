package cl.alexissilva.trainerapp.ui.nextworkout

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.core.domain.GroupSet
import cl.alexissilva.trainerapp.core.domain.Workout
import cl.alexissilva.trainerapp.core.domain.WorkoutExercise
import cl.alexissilva.trainerapp.core.testutils.DummyData
import cl.alexissilva.trainerapp.testutils.SwipeRefreshLayoutMatchers.isRefreshing
import cl.alexissilva.trainerapp.testutils.launchFragmentInHiltContainer
import cl.alexissilva.trainerapp.testutils.simulateSwipeToRefresh
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

@Config(instrumentedPackages = ["androidx.loader.content"])
@RunWith(AndroidJUnit4::class)
class NextWorkoutFragmentTest {

    private val exercises = listOf(
        WorkoutExercise(DummyData.exercise, groupSets = listOf(GroupSet(3, 10))),
        WorkoutExercise(DummyData.exercise2, groupSets = listOf(GroupSet(3, 10))),
    )
    private val workout = Workout("1", "Workout", 1, exercises)
    private lateinit var viewModel: NextWorkoutViewModel

    @Before
    fun setUp() {
        viewModel = mock {
            on { workout } doReturn MutableStateFlow(workout)
            on { errorMessage } doReturn MutableStateFlow(null)
            on { isLoading } doReturn MutableStateFlow(false)
        }
    }

    private fun launchFragment(action: NextWorkoutFragment.() -> Unit = {}) =
        launchFragmentInHiltContainer(
            themeResId = R.style.Theme_TrainerApp,
            instantiate = { NextWorkoutFragment(viewModel) },
            onFragmentAction = action
        )


    @Test
    fun navigatesToWorkoutLogActivity_onPressDone() {
        val navController = mock<NavController>()
        launchFragment {
            Navigation.setViewNavController(this.requireView(), navController)
        }
        onView(withId(R.id.done_button)).perform(click())

        verify(navController).navigate(
            NextWorkoutFragmentDirections.actionNextWorkoutFragmentToWorkoutLogActivity().apply {
                workoutId = workout.id
            }
        )
    }

    @Test
    fun updatesWorkoutStatus_onPressSkip() {
        launchFragment()
        onView(withId(R.id.skip_button)).perform(click())
        verify(viewModel).skipWorkout()
    }

    @Test
    fun downloadsWorkouts_onSwipeDown() {
        launchFragment {
            this.simulateSwipeToRefresh(R.id.swipeRefreshLayout)
        }
        verify(viewModel).syncWorkouts()
    }

    @Test
    fun showsWorkoutAndExercisesName() {
        launchFragment()
        onView(withId(R.id.workoutName_textView)).check(matches(withText(workout.name)))
        for (exercise in exercises) {
            onView(withText(exercise.exercise.name)).check(matches(isDisplayed()))
        }
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