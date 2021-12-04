package cl.alexissilva.trainerapp.ui.base

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.alexissilva.trainerapp.TestBindingActivity
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@Config(instrumentedPackages = ["androidx.loader.content"])
@RunWith(AndroidJUnit4::class)
class BindingActivityTest {
    @get:Rule
    var activityRule = activityScenarioRule<TestBindingActivity>()

    @Test
    fun initBinding_onCreate() {
        activityRule.scenario.onActivity {
            assertThat(it.bindingPublic).isNotNull()
        }

    }

    @Test
    fun clearBinding_onDestroy() {
        activityRule.scenario.onActivity {
            activityRule.scenario.moveToState(Lifecycle.State.DESTROYED)
            assertThat(it.bindingPublic).isNull()
        }
    }
}