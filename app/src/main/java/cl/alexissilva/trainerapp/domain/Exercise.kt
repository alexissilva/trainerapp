package cl.alexissilva.trainerapp.domain

data class Exercise(
    val id: String,
    val name: String,
    val image: String? = null,
)