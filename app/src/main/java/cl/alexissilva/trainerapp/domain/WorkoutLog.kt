package cl.alexissilva.trainerapp.domain

import java.time.LocalDateTime

data class WorkoutLog(
    val id: String,
    val workoutId: String,
    var status: WorkoutStatus = WorkoutStatus.UNKNOWN,
    val date: LocalDateTime = LocalDateTime.MIN,
    val comment: String? = null,
    val exerciseLogs: List<ExerciseLog> = emptyList(),
    var workout: Workout? = null
)

enum class WorkoutStatus {
    UNKNOWN,
    DONE,
    SKIPPED,
}
