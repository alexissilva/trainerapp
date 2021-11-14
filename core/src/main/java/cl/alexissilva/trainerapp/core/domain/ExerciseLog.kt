package cl.alexissilva.trainerapp.core.domain

data class ExerciseLog(
    val exercise: Exercise,
    val comment: String? = null,
    val setLogs: List<SetLog> = emptyList(),
)