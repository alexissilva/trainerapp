package cl.alexissilva.trainerapp.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.core.domain.WorkoutLog
import cl.alexissilva.trainerapp.core.domain.WorkoutStatus
import cl.alexissilva.trainerapp.databinding.WorkoutLogRowItemBinding
import cl.alexissilva.trainerapp.ui.base.BindingListAdapter
import com.bumptech.glide.Glide
import java.time.format.DateTimeFormatter

class LogsWithWorkoutAdapter(
    private val onItemClick: ((WorkoutLog) -> Unit)? = null
) : BindingListAdapter<WorkoutLog, WorkoutLogRowItemBinding>() {

    companion object {
        const val DATE_FORMAT = "EEEE, d MMMM"
    }

    override val inflateBinding: (LayoutInflater, ViewGroup, Boolean) -> WorkoutLogRowItemBinding
        get() = WorkoutLogRowItemBinding::inflate

    override fun WorkoutLogRowItemBinding.onBind(log: WorkoutLog, position: Int) {
        val workout = log.workout ?: run {
            Log.w("LogsWithWorkoutAdapter", "Log without workout! $log")
            return
        }

        val formattedDate = log.date.format(DateTimeFormatter.ofPattern(DATE_FORMAT))
        dateTextView.text = formattedDate
        nameTextView.text = workout.name
        cardView.setOnClickListener {
            onItemClick?.invoke(log)
        }

        val drawableId: Int = when (log.status) {
            WorkoutStatus.UNKNOWN -> {
                R.drawable.ic_unknown
            }
            WorkoutStatus.DONE -> {
                R.drawable.ic_done
            }
            //FIXME skip icon is not shown... something happens with the color
            WorkoutStatus.SKIPPED -> {
                R.drawable.ic_skip
            }
        }
        Glide.with(root.context)
            .asBitmap()
            .load(drawableId)
            .into(statusImageView)
    }

}