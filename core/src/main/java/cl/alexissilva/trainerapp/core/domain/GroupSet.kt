package cl.alexissilva.trainerapp.core.domain

data class GroupSet(
    val sets: Int,
    val reps: Int,
    val intensity: String? = null,
)
