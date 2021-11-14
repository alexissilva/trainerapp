package cl.alexissilva.trainerapp.ui.workouts

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
import cl.alexissilva.trainerapp.databinding.WorkoutRowItemBinding
import cl.alexissilva.trainerapp.core.domain.Workout
import cl.alexissilva.trainerapp.testutils.launchFragmentInHiltContainer
import cl.alexissilva.trainerapp.ui.base.RecyclerAdapterBinding
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
class WorkoutsFragmentTest {

    private val workouts = listOf(
        Workout("1", "workout 1"),
        Workout("1", "workout 2"),
        Workout("1", "workout 3")
    )
    private lateinit var viewModel: WorkoutsViewModel

    @Before
    fun setUp() {
        viewModel = mock {
            on { workouts } doReturn MutableStateFlow(workouts)
        }
    }

    @Test
    fun showsWorkoutList() {
        launchFragmentInHiltContainer { WorkoutsFragment(viewModel) }
        for (workout in workouts) {
            onView(withText(workout.name)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun showsNoWorkoutsText_whenWorkoutListIsEmpty() {
        val emptyFlow = MutableStateFlow(emptyList<Workout>())
        whenever(viewModel.workouts).thenReturn(emptyFlow)
        launchFragmentInHiltContainer { WorkoutsFragment(viewModel) }
        onView(withId(R.id.noWorkouts_textView)).check(matches(isDisplayed()))
    }

    @Test
    fun navigatesToWorkoutDetails_onItemClick() {
        val navController = mock<NavController>()
        launchFragmentInHiltContainer(
            instantiate = { WorkoutsFragment(viewModel) },
            onFragmentAction = {
                Navigation.setViewNavController(this.requireView(), navController)
            })
        onView(withId(R.id.workouts_recyclerView)).perform(
            actionOnItemAtPosition<RecyclerAdapterBinding.ViewHolderBinding<WorkoutRowItemBinding>>(
                1,
                click()
            )
        )
        verify(navController).navigate(
            WorkoutsFragmentDirections.actionWorkoutsFragmentToWorkoutDetailsActivity("1")
        )
    }


}