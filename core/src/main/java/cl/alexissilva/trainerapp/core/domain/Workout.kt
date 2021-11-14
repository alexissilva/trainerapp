package cl.alexissilva.trainerapp.core.domain

data class Workout(
    val id: String,
    val name: String,
    val day: Int? = null,
    val exercises: List<WorkoutExercise> = emptyList(),
)


