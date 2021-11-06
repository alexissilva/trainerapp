package cl.alexissilva.trainerapp.framework.database.workoutlog

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import cl.alexissilva.trainerapp.domain.ExerciseLog
import cl.alexissilva.trainerapp.domain.WorkoutStatus
import cl.alexissilva.trainerapp.framework.database.workout.WorkoutEntity
import java.time.LocalDateTime

@Entity(tableName = "workout_log")
data class WorkoutLogEntity(
    @PrimaryKey
    var id: String,
    var workoutId: String,
    var status: WorkoutStatus,
    var date: LocalDateTime,
    var comment: String?,
    var exerciseLogs: List<ExerciseLog>
)


data class WorkoutLogWithWorkout(
    @Embedded val workoutLog: WorkoutLogEntity,
    @Relation(
        parentColumn = "workoutId",
        entityColumn = "id"
    )
    val workout: WorkoutEntity
)
