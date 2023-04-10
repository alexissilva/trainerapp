package cl.alexissilva.trainerapp.ui.adapters.exerciselogs

import android.text.Editable
import cl.alexissilva.trainerapp.databinding.ItemExerciseLogRowBinding
import cl.alexissilva.trainerapp.databinding.ItemSetLogRowBinding
import cl.alexissilva.trainerapp.ui.base.MultipleBindingViewHolder
import cl.alexissilva.trainerapp.ui.views.WatchableEditText
import cl.alexissilva.trainerapp.utils.extensions.showShortToast


class ExerciseHeaderHolder(binding: ItemExerciseLogRowBinding) :
    MultipleBindingViewHolder<ExerciseLogItem, ItemExerciseLogRowBinding>(binding) {

    override fun ItemExerciseLogRowBinding.onBind(item: ExerciseLogItem, position: Int) {
        exerciseTextView.text = (item as ExerciseHeader).exercise.name
    }
}


class SetLogItemHolder(binding: ItemSetLogRowBinding) :
    MultipleBindingViewHolder<ExerciseLogItem, ItemSetLogRowBinding>(binding) {

    override fun ItemSetLogRowBinding.onBind(item: ExerciseLogItem, position: Int) {
        val item = item as SetLogItem
        setTextView.text = "Set ${item.number}"
        repsEditText.setValueAndUpdateModel(item.reps) { item.reps = it }
        weightEditText.setValueAndUpdateModel(item.weight) { item.weight = it }
        repsEditText.isEnabled = item.done
        weightEditText.isEnabled = item.done

        doneCheckBox.onCheckedChange = null
        doneCheckBox.isChecked = item.done
        doneCheckBox.onCheckedChange = { _, isChecked ->
            item.done = isChecked
            repsEditText.isEnabled = item.done
            weightEditText.isEnabled = item.done
            //TODO pass adapter reference or do update manually(?)
            //notifyItemChanged(position)
        }
    }

    override fun ItemSetLogRowBinding.onAttachedToWindow() {
        repsEditText.startWatching()
        weightEditText.startWatching()
        doneCheckBox.startWatching()
    }

    override fun ItemSetLogRowBinding.onDetachedFromWindow() {
        repsEditText.stopWatching()
        weightEditText.stopWatching()
        doneCheckBox.stopWatching()
    }


    //TODO move to other file? WatchableEditText or create new EditText?
    private fun WatchableEditText.setValueAndUpdateModel(
        currentValue: Int?,
        updateModel: (newValue: Int) -> Unit
    ) {
        val editableToInt = { editable: Editable -> editable.toString().toIntOrNull() }
        val text = currentValue?.toString() ?: ""
        setTextAndUpdateModel(text, editableToInt, updateModel)
    }

    private fun <T> WatchableEditText.setTextAndUpdateModel(
        text: String,
        parseText: Editable.() -> T?,
        updateModel: (T) -> Unit
    ) {
        setText(text)
        afterTextChanged = {
            val parsedText = it?.parseText()
            if (parsedText != null) {
                updateModel(parsedText)
            } else {
                context.showShortToast("Invalid text")
            }
        }

    }
}
