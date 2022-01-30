package cl.alexissilva.trainerapp.ui.adapters.exerciselogs

import cl.alexissilva.trainerapp.core.domain.Exercise

sealed class ExerciseLogItem

class ExerciseHeader(
    val exerciseLogId: String,
    val exercise: Exercise,
    var comment: String? = null
) : ExerciseLogItem()

class SetLogItem(
    val setLogId: String,
    val number: Int,
    var done: Boolean = true,
    var reps: Int? = null,
    var weight: Int? = null,
    val readOnly: Boolean = false,
) : ExerciseLogItem()

enum class ExerciseLogItemType {
    HEADER, SET_LOG
}
