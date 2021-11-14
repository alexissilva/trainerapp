package cl.alexissilva.trainerapp.framework.database.workout

import androidx.room.Entity
import androidx.room.PrimaryKey
import cl.alexissilva.trainerapp.core.domain.WorkoutExercise

@Entity(tableName = "workout")
data class WorkoutEntity(
    @PrimaryKey
    var id: String,
    var name: String,
    var day: Int?,
    var exercises: List<WorkoutExercise>,
)
