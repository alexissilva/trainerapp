package cl.alexissilva.trainerapp.domain

import java.time.LocalDate

//TODO rename to "Workout"
data class Session(
    val id: String,
    val name: String,
    val date: LocalDate = LocalDate.MIN,
    val exercises: List<Exercise> = emptyList(),
    var status: SessionStatus = SessionStatus.PENDING
)


enum class SessionStatus {
    PENDING,
    DONE,
    SKIPPED,
}
