package cl.alexissilva.trainerapp.core.domain

import java.time.LocalDate

data class Microcycle(
    val id: String,
    val name: String,
    val startDate: LocalDate,
    val durationDays: Int = 7,
    val restDaysRegime: RestDaysRegime = RestDaysRegime.FREE,
    val workouts: List<Workout>? = null,
)

enum class RestDaysRegime {
    FREE, //You decide which day to train or rest.
    SUGGESTED, //Trainer suggest rest days, but you can change them.
    MANDATORY, //You must rest on indicated dates. The microcycle doesn't allow changes.
}