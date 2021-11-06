package cl.alexissilva.trainerapp.domain

data class WorkoutExercise(
    val exercise: Exercise,
    val groupSets: List<GroupSet> = emptyList()
)
