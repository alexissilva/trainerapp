package cl.alexissilva.trainerapp.ui.adapters

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.core.domain.WorkoutLog
import cl.alexissilva.trainerapp.testutils.AdapterTestUtils.getBoundViewHolder
import cl.alexissilva.trainerapp.core.testutils.DummyData
import com.google.common.truth.Truth.assertThat

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LogsWithWorkoutAdapterTest {
    private lateinit var adapter: LogsWithWorkoutAdapter
    private lateinit var logList: List<WorkoutLog>
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setUp() {
        logList = listOf(
            DummyData.workoutLog.apply { workout = DummyData.workout },
            DummyData.workoutLog2.apply { workout = DummyData.workout2 },
        )
        adapter = LogsWithWorkoutAdapter()
        adapter.dataList = logList
    }

    @Test
    fun onBindViewHolder_setsLogData() {
        for (index in logList.indices) {
            val log = logList[index]
            val viewHolder = getBoundViewHolder(index)
            assertThat(viewHolder.binding.nameTextView.text).isEqualTo(log.workout?.name)
        }
    }

    private fun getBoundViewHolder(index: Int) =
        getBoundViewHolder(context, adapter, R.layout.workout_log_row_item, index)


}