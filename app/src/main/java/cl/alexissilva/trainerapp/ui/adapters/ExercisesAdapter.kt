package cl.alexissilva.trainerapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import cl.alexissilva.trainerapp.core.domain.WorkoutExercise
import cl.alexissilva.trainerapp.databinding.ExerciseRowItemBinding
import cl.alexissilva.trainerapp.ui.base.BindingListAdapter
import com.bumptech.glide.Glide

class ExercisesAdapter : BindingListAdapter<WorkoutExercise, ExerciseRowItemBinding>() {

    override val inflateBinding: (LayoutInflater, ViewGroup, Boolean) -> ExerciseRowItemBinding
        get() = ExerciseRowItemBinding::inflate

    override fun ExerciseRowItemBinding.onBind(exercise: WorkoutExercise, position: Int) {
        nameTextView.text = exercise.exercise.name
        groupSetsRecyclerView.adapter = GroupSetsAdapter().apply { submitList(exercise.groupSets) }
        groupSetsRecyclerView.layoutManager = LinearLayoutManager(context)

        Glide.with(context).load(exercise.exercise.image).centerCrop().into(exerciseImageView)
    }
}