package cl.alexissilva.trainerapp.ui.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.domain.Session
import cl.alexissilva.trainerapp.ui.adapters.SessionsAdapter
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class SessionsAdapterTest {

    private lateinit var adapter: SessionsAdapter
    private lateinit var items: List<Session>
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setUp() {
        items = listOf(
            Session("session1", "date", LocalDate.of(2020, 1, 1)),
            Session("session2", "date"),
            Session("session3", "date"),
        )
        adapter = SessionsAdapter(context)
        adapter.setSessionList(items)
    }

    @Test
    fun getItemCount() {
        assertThat(adapter.itemCount).isEqualTo(3)
    }

    @Test
    fun onBindViewHolder() {
        val index = 0
        val session = items[index]
        val viewHolder = getBindViewHolder(index)

        assertThat(viewHolder.binding.nameTextView.text).isEqualTo(session.name)
        assertThat(viewHolder.binding.dateTextView.text).isEqualTo("Wednesday, 1 January")
    }

    @Test
    fun doesNotShowStatusImageByDefault() {
        val index = 0
        val viewHolder = getBindViewHolder(index)

        assertThat(viewHolder.binding.statusImageView.visibility).isEqualTo(View.INVISIBLE)
    }

    @Test
    fun showStatusImage_whenShowStatusIsTrue() {
        adapter = SessionsAdapter(context, true)
        adapter.setSessionList(items)

        val viewHolder = getBindViewHolder(0)
        assertThat(viewHolder.binding.statusImageView.visibility).isEqualTo(View.VISIBLE)
    }

    @Test
    fun setSessionList() {
        //TODO
    }

    private fun getBindViewHolder(index: Int): SessionsAdapter.ViewHolder {
        val rowView = View.inflate(context, R.layout.sessions_row_item, null) as ViewGroup
        val viewHolder = adapter.createViewHolder(rowView, 0)
        adapter.bindViewHolder(viewHolder, index)
        return viewHolder
    }
}