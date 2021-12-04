package cl.alexissilva.trainerapp.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.alexissilva.trainerapp.testutils.TestViewBinding
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

class TestBindingFragment : BindingFragment<TestViewBinding>() {
    override val inflateBinding: (LayoutInflater, ViewGroup?, Boolean) -> TestViewBinding
        get() = TestViewBinding.inflate

    val bindingPublic
        get() = try {
            binding
        } catch (e: NullPointerException) {
            null
        }
}

@RunWith(AndroidJUnit4::class)
class BindingFragmentTest {
    private lateinit var scenario: FragmentScenario<TestBindingFragment>

    @Before
    fun setUp() {
        scenario = launchFragmentInContainer()
    }

    @Test
    fun initBinding_onCreateView() {
        scenario.onFragment {
            assertThat(it.bindingPublic).isNotNull()
        }
    }

    @Test
    fun clearBinding_onDestroyView() {
        scenario.onFragment {
            scenario.moveToState(Lifecycle.State.DESTROYED)
            assertThat(it.bindingPublic).isNull()
        }
    }
}