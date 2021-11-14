package cl.alexissilva.trainerapp.ui.adapters

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.core.domain.Workout
import cl.alexissilva.trainerapp.testutils.AdapterTestUtils.getBoundViewHolder
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
            Workout("1", "workout1"),
            Workout("2", "workout2"),
            Workout("3", "workout3"),
        )
        adapter = WorkoutsAdapter()
        adapter.dataList = workoutList
    }

    @Test
    fun getItemCount() {
        assertThat(adapter.itemCount).isEqualTo(3)
    }

    @Test
    fun onBindViewHolder_setsWorkoutData() {
        for (index in workoutList.indices) {
            val workout = workoutList[index]
            val viewHolder = getBoundViewHolder(index)
            assertThat(viewHolder.binding.nameTextView.text).isEqualTo(workout.name)
        }
    }

    @Test
    fun setWorkoutList_updateData() {
        val workoutList = listOf(Workout("new", "new"))
        adapter.dataList = workoutList

        val viewHolder = getBoundViewHolder(0)
        assertThat(viewHolder.binding.nameTextView.text).isEqualTo("new")
    }

    private fun getBoundViewHolder(index: Int) =
        getBoundViewHolder(context, adapter, R.layout.workout_row_item, index)
}