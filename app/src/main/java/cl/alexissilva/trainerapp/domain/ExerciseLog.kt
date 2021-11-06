package cl.alexissilva.trainerapp.domain

data class ExerciseLog(
    val exercise: Exercise,
    val comment: String? = null,
    val setLogs: List<SetLog> = emptyList(),
)