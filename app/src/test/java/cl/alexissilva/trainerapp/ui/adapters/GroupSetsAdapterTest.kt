package cl.alexissilva.trainerapp.ui.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.core.domain.GroupSet
import cl.alexissilva.trainerapp.ui.adapters.GroupSetsAdapter
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class GroupSetsAdapterTest {

    private lateinit var adapter: GroupSetsAdapter
    private lateinit var items: List<GroupSet>
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setUp() {
        items = listOf(
            GroupSet(1, 5, "80%"),
            GroupSet(1, 3),
            GroupSet(1, 1),
        )
        adapter = GroupSetsAdapter(items)
    }

    @Test
    fun getItemCount() {
        assertThat(adapter.itemCount).isEqualTo(3)
    }

    @Test
    fun bind_setDescriptionWithIntensity() {
        val index = 0
        val viewHolder = getBindViewHolder(index)
        val expectedDescription =
            "${items[index].sets}x${items[index].reps} at ${items[index].intensity}"
        assertThat(viewHolder.binding.descriptionTextView.text).isEqualTo(expectedDescription)
    }


    @Test
    fun bind_setDescriptionWithoutIntensity() {
        val index = 1
        val viewHolder = getBindViewHolder(index)
        val expectedDescription = "${items[index].sets}x${items[index].reps}"
        assertThat(viewHolder.binding.descriptionTextView.text).isEqualTo(expectedDescription)
    }


    private fun getBindViewHolder(index: Int): GroupSetsAdapter.ViewHolder {
        val rowView = View.inflate(context, R.layout.group_set_row_item, null) as ViewGroup
        val viewHolder = adapter.createViewHolder(rowView, 0)
        adapter.bindViewHolder(viewHolder, index)
        return viewHolder
    }
}