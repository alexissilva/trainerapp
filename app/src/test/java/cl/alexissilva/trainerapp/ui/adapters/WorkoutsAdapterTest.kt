package cl.alexissilva.trainerapp.ui.adapters

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.core.domain.Workout
import cl.alexissilva.trainerapp.core.testutils.DummyData
import cl.alexissilva.trainerapp.testutils.AdapterTestUtils
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class WorkoutsAdapterTest {

    private lateinit var adapter: WorkoutsAdapter
    private lateinit var workoutList: List<Workout>
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setUp() {
        workoutList = listOf(
            DummyData.workout,
            DummyData.workout2,
            DummyData.workout3,
        )
        adapter = WorkoutsAdapter()
        adapter.submitList(workoutList)
    }


    @Test
    fun showsWorkoutName() {
        for (index in workoutList.indices) {
            val workout = workoutList[index]
            val viewHolder = getBoundViewHolder(index)
            assertThat(viewHolder.binding.nameTextView.text).isEqualTo(workout.name)
        }
    }

    private fun getBoundViewHolder(index: Int) =
        AdapterTestUtils.getBoundViewHolder(context, adapter, R.layout.workout_row_item, index)
}