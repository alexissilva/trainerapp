package cl.alexissilva.trainerapp.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.databinding.WorkoutLogRowItemBinding
import cl.alexissilva.trainerapp.domain.WorkoutLog
import cl.alexissilva.trainerapp.domain.WorkoutStatus
import cl.alexissilva.trainerapp.ui.base.RecyclerAdapterBindingList
import com.bumptech.glide.Glide
import java.time.format.DateTimeFormatter

class LogsWithWorkoutAdapter(
    private val onItemClick: ((WorkoutLog) -> Unit)? = null
) : RecyclerAdapterBindingList<WorkoutLogRowItemBinding, WorkoutLog>() {

    companion object {
        const val DATE_FORMAT = "EEEE, d MMMM"
    }

    override val inflateBinding: (LayoutInflater, ViewGroup, Boolean) -> WorkoutLogRowItemBinding
        get() = WorkoutLogRowItemBinding::inflate

    override fun onBindViewHolder(
        holder: ViewHolderBinding<WorkoutLogRowItemBinding>,
        position: Int
    ) {
        val log = dataList[position]
        val workout = log.workout ?: run {
            Log.w("LogsWithWorkoutAdapter", "Log without workout! $log")
            return
        }

        val formattedDate = log.date.format(DateTimeFormatter.ofPattern(DATE_FORMAT))
        holder.binding.dateTextView.text = formattedDate

        holder.binding.nameTextView.text = workout.name
        holder.binding.cardView.setOnClickListener {
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
        Glide.with(holder.binding.root.context)
            .asBitmap()
            .load(drawableId)
            .into(holder.binding.statusImageView)
    }

}