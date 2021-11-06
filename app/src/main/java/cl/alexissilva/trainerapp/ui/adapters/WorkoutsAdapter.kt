package cl.alexissilva.trainerapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.databinding.WorkoutRowItemBinding
import cl.alexissilva.trainerapp.domain.Workout
import cl.alexissilva.trainerapp.ui.base.RecyclerAdapterBindingList

class WorkoutsAdapter(
    private val onItemClick: ((Workout) -> Unit)? = null
) : RecyclerAdapterBindingList<WorkoutRowItemBinding, Workout>() {

    override val inflateBinding: (LayoutInflater, ViewGroup, Boolean) -> WorkoutRowItemBinding
        get() = WorkoutRowItemBinding::inflate

    override fun onBindViewHolder(holder: ViewHolderBinding<WorkoutRowItemBinding>, position: Int) {
        val workout = dataList[position]
        holder.binding.dayTextView.text = context.getString(R.string.workout_day, workout.day)
        holder.binding.nameTextView.text = workout.name
        holder.binding.cardView.setOnClickListener {
            onItemClick?.invoke(workout)
        }
    }

}