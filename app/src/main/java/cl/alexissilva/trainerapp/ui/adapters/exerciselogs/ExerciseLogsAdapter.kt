package cl.alexissilva.trainerapp.ui.adapters.exerciselogs

import android.view.LayoutInflater
import android.view.ViewGroup
import cl.alexissilva.trainerapp.core.domain.ExerciseLog
import cl.alexissilva.trainerapp.databinding.ItemExerciseLogRowBinding
import cl.alexissilva.trainerapp.databinding.ItemSetLogRowBinding
import cl.alexissilva.trainerapp.ui.adapters.exerciselogs.ExerciseLogItemMapper.mapItemsToLogs
import cl.alexissilva.trainerapp.ui.adapters.exerciselogs.ExerciseLogItemMapper.mapLogsToItems
import cl.alexissilva.trainerapp.ui.base.MultipleBindingListAdapter
import cl.alexissilva.trainerapp.ui.base.MultipleBindingViewHolder
import javax.inject.Inject

class ExerciseLogsAdapter @Inject constructor() :
    MultipleBindingListAdapter<ExerciseLogItemType, ExerciseLogItem>() {

    override fun onCreateViewHolderByType(
        inflater: LayoutInflater,
        parent: ViewGroup,
        attachToParent: Boolean,
        viewType: ExerciseLogItemType,
    ): MultipleBindingViewHolder<ExerciseLogItem, *> {
        return when (viewType) {
            ExerciseLogItemType.HEADER -> {
                val binding = ItemExerciseLogRowBinding.inflate(inflater, parent, attachToParent)
                ExerciseHeaderHolder(binding)
            }
            ExerciseLogItemType.SET_LOG -> {
                val binding = ItemSetLogRowBinding.inflate(inflater, parent, attachToParent)
                SetLogItemHolder(binding)
            }
        }
    }

    override val enumValues = ExerciseLogItemType.values()

    override fun getItemViewType(item: ExerciseLogItem, position: Int): ExerciseLogItemType {
        return when (item) {
            is ExerciseHeader -> ExerciseLogItemType.HEADER
            is SetLogItem -> ExerciseLogItemType.SET_LOG
        }
    }

    fun setExerciseLogs(exerciseLogs: List<ExerciseLog>, readOnly: Boolean = false) {
        submitList(mapLogsToItems(exerciseLogs, readOnly))
    }

    fun getExerciseLogs(): List<ExerciseLog> {
        return mapItemsToLogs(currentList)
    }

}
