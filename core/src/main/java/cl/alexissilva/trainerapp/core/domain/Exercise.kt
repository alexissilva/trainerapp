package cl.alexissilva.trainerapp.core.domain

data class Exercise(
    val id: String,
    val name: String,
    val image: String? = null,
)