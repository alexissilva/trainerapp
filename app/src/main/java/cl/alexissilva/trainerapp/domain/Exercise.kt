package cl.alexissilva.trainerapp.domain

data class Exercise(
    val name: String,
    val image: String? = null,
    val groupSets : List<GroupSet> = emptyList()
)
