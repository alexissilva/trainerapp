package cl.alexissilva.trainerapp.ui.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.domain.Workout
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class WorkoutsAdapterTest {

    private lateinit var adapter: WorkoutsAdapter
    private lateinit var workoutList: List<Workout>
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setUp() {
        workoutList = listOf(
            Workout("1", "workout1", LocalDate.of(2020, 1, 1)),
            Workout("2", "workout2"),
            Workout("3", "workout3"),
        )
        adapter = WorkoutsAdapter(context)
        adapter.setWorkoutList(workoutList)
    }

    @Test
    fun getItemCount() {
        assertThat(adapter.itemCount).isEqualTo(3)
    }

    @Test
    fun onBindViewHolder_setsWorkoutData() {
        val index = 0
        val workout = workoutList[index]
        val viewHolder = getBoundViewHolder(index)
        assertThat(viewHolder.binding.nameTextView.text).isEqualTo(workout.name)
        assertThat(viewHolder.binding.dateTextView.text).isEqualTo("Wednesday, 1 January")
    }

    @Test
    fun doesNotShowStatusImage_byDefault() {
        val index = 0
        val viewHolder = getBoundViewHolder(index)
        assertThat(viewHolder.binding.statusImageView.visibility).isEqualTo(View.INVISIBLE)
    }

    @Test
    fun showsStatusImage_whenShowStatusIsTrue() {
        adapter = WorkoutsAdapter(context, true)
        adapter.setWorkoutList(workoutList)
        val viewHolder = getBoundViewHolder(0)
        assertThat(viewHolder.binding.statusImageView.visibility).isEqualTo(View.VISIBLE)
    }

    @Test
    fun setWorkoutList_updateData() {
        val workoutList = listOf(Workout("new", "new"))
        adapter.setWorkoutList(workoutList)

        val viewHolder = getBoundViewHolder(0)
        assertThat(viewHolder.binding.nameTextView.text).isEqualTo("new")
    }

    private fun getBoundViewHolder(index: Int): WorkoutsAdapter.ViewHolder {
        val rowView = View.inflate(context, R.layout.workout_row_item, null) as ViewGroup
        val viewHolder = adapter.createViewHolder(rowView, 0)
        adapter.bindViewHolder(viewHolder, index)
        return viewHolder
    }
}