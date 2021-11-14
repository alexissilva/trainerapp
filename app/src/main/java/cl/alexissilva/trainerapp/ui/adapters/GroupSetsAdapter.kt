package cl.alexissilva.trainerapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cl.alexissilva.trainerapp.databinding.GroupSetRowItemBinding
import cl.alexissilva.trainerapp.core.domain.GroupSet

class GroupSetsAdapter(private val groupSetList: List<GroupSet>) :
    RecyclerView.Adapter<GroupSetsAdapter.ViewHolder>() {

    class ViewHolder(val binding: GroupSetRowItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = GroupSetRowItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = groupSetList[position]
        var description = "${exercise.sets}x${exercise.reps}"
        if (!exercise.intensity.isNullOrEmpty()) {
            description += " at ${exercise.intensity}"
        }
        holder.binding.descriptionTextView.text = description

    }

    override fun getItemCount(): Int {
        return groupSetList.size
    }

}