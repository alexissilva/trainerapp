package cl.alexissilva.trainerapp.core.domain

class SetLog(
    val id: String,
    val number: Int,
    val done: Boolean,
    //TODO renamed to reps and weight for simplicity(?)
    val repsDone: Int? = null,
    val weightUsed: Int? = null,
)