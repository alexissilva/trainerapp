package cl.alexissilva.trainerapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.databinding.WorkoutRowItemBinding
import cl.alexissilva.trainerapp.domain.Workout
import cl.alexissilva.trainerapp.domain.WorkoutStatus
import cl.alexissilva.trainerapp.ui.DefaultDiffUtil
import com.bumptech.glide.Glide
import java.time.format.DateTimeFormatter

class WorkoutsAdapter(
    private val context: Context,
    private val showStatus: Boolean = false,
    private val onItemClick: ((Workout) -> Unit)? = null
) : RecyclerView.Adapter<WorkoutsAdapter.ViewHolder>() {

    companion object {
        const val DATE_FORMAT = "EEEE, d MMMM"
    }

    private var workoutList = mutableListOf<Workout>()

    class ViewHolder(val binding: WorkoutRowItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = WorkoutRowItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val workout = workoutList[position]

        val formattedDate = workout.date.format(DateTimeFormatter.ofPattern(DATE_FORMAT))

        holder.binding.nameTextView.text = workout.name
        holder.binding.dateTextView.text = formattedDate
        holder.binding.cardView.setOnClickListener {
            onItemClick?.invoke(workout)
        }

        if (showStatus) {
            holder.binding.statusImageView.visibility = View.VISIBLE
            val drawableId: Int = when (workout.status) {
                WorkoutStatus.PENDING -> {
                    R.drawable.ic_pending
                }
                WorkoutStatus.DONE -> {
                    R.drawable.ic_done
                }
                //FIXME skip icon is not shown
                WorkoutStatus.SKIPPED -> {
                    R.drawable.ic_skip
                }
            }
            Glide.with(context)
                .asBitmap()
                .load(drawableId)
                .into(holder.binding.statusImageView)
        }
    }

    override fun getItemCount(): Int {
        return workoutList.size
    }

    fun setWorkoutList(newWorkoutList: List<Workout>) {
        val diffCallback = DefaultDiffUtil(workoutList, newWorkoutList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        workoutList.clear()
        workoutList.addAll(newWorkoutList)
        diffResult.dispatchUpdatesTo(this)
    }


}