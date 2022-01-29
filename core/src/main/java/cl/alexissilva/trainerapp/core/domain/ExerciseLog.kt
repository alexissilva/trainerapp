package cl.alexissilva.trainerapp.core.domain

data class ExerciseLog(
    val id: String,
    val exercise: Exercise,
    val comment: String? = null,
    var setLogs: List<SetLog> = emptyList(),
)