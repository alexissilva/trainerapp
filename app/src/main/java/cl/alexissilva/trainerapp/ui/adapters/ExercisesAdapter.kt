package cl.alexissilva.trainerapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cl.alexissilva.trainerapp.databinding.ExerciseRowItemBinding
import cl.alexissilva.trainerapp.domain.Exercise
import cl.alexissilva.trainerapp.ui.DefaultDiffUtil
import com.bumptech.glide.Glide

class ExercisesAdapter(private val context: Context) :
    RecyclerView.Adapter<ExercisesAdapter.ViewHolder>() {

    private var exerciseList = mutableListOf<Exercise>()

    class ViewHolder(val binding: ExerciseRowItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = ExerciseRowItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = exerciseList[position]

        holder.binding.nameTextView.text = exercise.name
        holder.binding.groupSetsRecyclerView.adapter = GroupSetsAdapter(exercise.groupSets)
        holder.binding.groupSetsRecyclerView.layoutManager = LinearLayoutManager(context)

        Glide.with(context).load(exercise.image).centerCrop().into(holder.binding.exerciseImageView)
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }

    fun setExerciseList(newExerciseList: List<Exercise>) {
        val diffCallback = DefaultDiffUtil(this.exerciseList, newExerciseList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.exerciseList.clear()
        this.exerciseList.addAll(newExerciseList)
        diffResult.dispatchUpdatesTo(this)
    }
}