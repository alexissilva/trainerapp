package cl.alexissilva.trainerapp.ui.adapters

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.core.domain.WorkoutExercise
import cl.alexissilva.trainerapp.core.testutils.DummyData
import cl.alexissilva.trainerapp.testutils.AdapterTestUtils
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
        adapter = ExercisesAdapter()
        adapter.submitList(items)
    }


    @Test
    fun showsExerciseNames() {
        for (index in items.indices) {
            val viewHolder = getBoundViewHolder(index)
            assertThat(viewHolder.binding.nameTextView.text).isEqualTo(items[index].exercise.name)
        }
    }

    private fun getBoundViewHolder(index: Int) =
        AdapterTestUtils.getBoundViewHolder(context, adapter, R.layout.exercise_row_item, index)

}