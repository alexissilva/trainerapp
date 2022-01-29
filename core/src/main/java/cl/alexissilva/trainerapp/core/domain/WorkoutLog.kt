package cl.alexissilva.trainerapp.core.domain

import java.time.LocalDateTime

data class WorkoutLog(
    val id: String,
    val workoutId: String,
    var status: WorkoutStatus = WorkoutStatus.UNKNOWN,
    val date: LocalDateTime = LocalDateTime.MIN,
    val comment: String? = null,
    var exerciseLogs: List<ExerciseLog> = emptyList(),
    var workout: Workout? = null
)

enum class WorkoutStatus {
    UNKNOWN,
    DONE,
    SKIPPED,
}
