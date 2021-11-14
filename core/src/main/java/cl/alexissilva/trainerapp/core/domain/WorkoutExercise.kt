package cl.alexissilva.trainerapp.core.domain

data class WorkoutExercise(
    val exercise: Exercise,
    val groupSets: List<GroupSet> = emptyList()
)
