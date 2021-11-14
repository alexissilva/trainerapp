package cl.alexissilva.trainerapp.core.domain

class SetLog(
    val id: String,
    val status: SetStatus = SetStatus.UNKNOWN,
    val repsDone: Int,
    val weightUsed: Int,
)

enum class SetStatus {
    UNKNOWN,
    DONE,
    SKIPPED,
}
