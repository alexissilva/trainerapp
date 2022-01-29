package cl.alexissilva.trainerapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import cl.alexissilva.trainerapp.core.domain.GroupSet
import cl.alexissilva.trainerapp.databinding.GroupSetRowItemBinding
import cl.alexissilva.trainerapp.ui.base.BindingListAdapter

class GroupSetsAdapter : BindingListAdapter<GroupSet, GroupSetRowItemBinding>() {

    override val inflateBinding: (LayoutInflater, ViewGroup, Boolean) -> GroupSetRowItemBinding
        get() = GroupSetRowItemBinding::inflate

    override fun GroupSetRowItemBinding.onBind(item: GroupSet, position: Int) {
        var description = "${item.sets}x${item.reps}"
        if (!item.intensity.isNullOrEmpty()) {
            description += " at ${item.intensity}"
        }
        descriptionTextView.text = description
    }

}