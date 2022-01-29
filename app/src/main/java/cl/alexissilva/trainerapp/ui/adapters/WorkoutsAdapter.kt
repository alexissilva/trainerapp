package cl.alexissilva.trainerapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.databinding.WorkoutRowItemBinding
import cl.alexissilva.trainerapp.core.domain.Workout
import cl.alexissilva.trainerapp.ui.base.BindingListAdapter

class WorkoutsAdapter(
    private val onItemClick: ((Workout) -> Unit)? = null
) : BindingListAdapter<Workout, WorkoutRowItemBinding>() {

    override val inflateBinding: (LayoutInflater, ViewGroup, Boolean) -> WorkoutRowItemBinding
        get() = WorkoutRowItemBinding::inflate

    override fun WorkoutRowItemBinding.onBind(workout: Workout, position: Int) {
        dayTextView.text = context.getString(R.string.workout_day, workout.day)
        nameTextView.text = workout.name
        cardView.setOnClickListener {
            onItemClick?.invoke(workout)
        }
    }

}