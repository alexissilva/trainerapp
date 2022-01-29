package cl.alexissilva.trainerapp.core.domain

data class WorkoutExercise(
    //TODO add position / number
    val exercise: Exercise,
    val groupSets: List<GroupSet> = emptyList()
)
