package cl.alexissilva.trainerapp.ui.sessiondetails

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.domain.Session
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
class SessionDetailsActivityTest {

    private val dummySession = Session("1", "dummy name", LocalDate.of(2020,1,1))

    @get:Rule
    var activityScenarioRule = activityScenarioRule<SessionDetailsActivity>()

    private lateinit var viewModel: SessionDetailsViewModel

    @Before
    fun setUp() {
        viewModel = mock {
            on { session } doReturn MutableStateFlow(dummySession)
        }
    }

    @Test
    fun showSessionData() {
        activityScenarioRule.scenario.onActivity {
            it.setupViewModel(viewModel)
        }
        onView(withId(R.id.date_textView)).check(matches(withText("Wednesday, 1 January")))
    }
}