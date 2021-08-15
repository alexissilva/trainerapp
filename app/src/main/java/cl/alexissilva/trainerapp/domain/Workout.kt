package cl.alexissilva.trainerapp.domain

import java.time.LocalDate

data class Workout(
    val id: String,
    val name: String,
    val date: LocalDate = LocalDate.MIN,
    val exercises: List<Exercise> = emptyList(),
    var status: WorkoutStatus = WorkoutStatus.PENDING
)


enum class WorkoutStatus {
    PENDING,
    DONE,
    SKIPPED,
}
