package cl.alexissilva.trainerapp.ui.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.domain.WorkoutExercise
import cl.alexissilva.trainerapp.testutils.DummyData
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ExercisesAdapterTest {

    private lateinit var adapter: ExercisesAdapter
    private lateinit var items: List<WorkoutExercise>
    private val context = ApplicationProvider.getApplicationContext<Context>()


    @Before
    fun setUp() {
        items = listOf(
            WorkoutExercise(DummyData.exercise),
            WorkoutExercise(DummyData.exercise2)
        )
        adapter = ExercisesAdapter(context)
        adapter.setExerciseList(items)
    }

    @Test
    fun getItemCount() {
        assertThat(adapter.itemCount).isEqualTo(2)
    }

    @Test
    fun bindExerciseInfo() {
        val index = 0
        val rowView = View.inflate(context, R.layout.exercise_row_item, null) as ViewGroup
        val viewHolder = adapter.createViewHolder(rowView, 0)
        adapter.bindViewHolder(viewHolder, index)
        assertThat(viewHolder.binding.nameTextView.text).isEqualTo(items[index].exercise.name)
    }
}