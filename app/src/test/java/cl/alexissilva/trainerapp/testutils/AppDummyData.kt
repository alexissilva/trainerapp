package cl.alexissilva.trainerapp.testutils

import cl.alexissilva.trainerapp.core.domain.WorkoutStatus
import cl.alexissilva.trainerapp.framework.database.workout.WorkoutEntity
import cl.alexissilva.trainerapp.framework.database.workoutlog.WorkoutLogEntity
import java.time.LocalDateTime

object AppDummyData {

    val workoutEntity = WorkoutEntity("id", "name", 1, emptyList())
    val workoutEntity2 = WorkoutEntity("id2", "name2", 2, emptyList())

    val workoutLogEntity = WorkoutLogEntity(
        "id", "workoutId", WorkoutStatus.UNKNOWN, LocalDateTime.MIN, null, emptyList()
    )
    val workoutLogEntity2 = WorkoutLogEntity(
        "id2", "workoutId2", WorkoutStatus.UNKNOWN, LocalDateTime.MIN, null, emptyList()
    )
}