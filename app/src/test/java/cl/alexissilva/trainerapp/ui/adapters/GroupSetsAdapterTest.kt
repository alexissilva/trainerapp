package cl.alexissilva.trainerapp.ui.adapters

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.core.domain.GroupSet
import cl.alexissilva.trainerapp.testutils.AdapterTestUtils
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
        adapter = GroupSetsAdapter()
        adapter.submitList(items)
    }

    @Test
    fun getItemCount() {
        assertThat(adapter.itemCount).isEqualTo(3)
    }

    @Test
    fun showsDescriptionWithIntensity() {
        val index = 0
        val viewHolder = getBoundViewHolder(index)
        val expectedDescription =
            "${items[index].sets}x${items[index].reps} at ${items[index].intensity}"
        assertThat(viewHolder.binding.descriptionTextView.text).isEqualTo(expectedDescription)
    }


    @Test
    fun showsDescriptionWithoutIntensity() {
        val index = 1
        val viewHolder = getBoundViewHolder(index)
        val expectedDescription = "${items[index].sets}x${items[index].reps}"
        assertThat(viewHolder.binding.descriptionTextView.text).isEqualTo(expectedDescription)
    }

    private fun getBoundViewHolder(index: Int) =
        AdapterTestUtils.getBoundViewHolder(context, adapter, R.layout.group_set_row_item, index)
}