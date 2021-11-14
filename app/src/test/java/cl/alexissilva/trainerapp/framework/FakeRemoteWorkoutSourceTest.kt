package cl.alexissilva.trainerapp.framework

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.alexissilva.trainerapp.core.data.RemoteResult
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(AndroidJUnit4::class)
class FakeRemoteWorkoutSourceTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var gson: Gson

    private lateinit var fakeSource: FakeRemoteWorkoutSource

    @Before
    fun setUp() {
        hiltRule.inject()
        fakeSource = FakeRemoteWorkoutSource(context, gson)
    }

    @Test
    fun getWorkoutsSuccessfully() = runBlockingTest {
        val workouts = fakeSource.getWorkouts()
        assertThat(workouts).isInstanceOf(RemoteResult.Success::class.java)
    }
}