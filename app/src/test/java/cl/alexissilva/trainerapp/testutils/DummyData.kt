package cl.alexissilva.trainerapp.testutils

import cl.alexissilva.trainerapp.domain.Exercise
import cl.alexissilva.trainerapp.domain.Workout
import cl.alexissilva.trainerapp.domain.WorkoutLog
import cl.alexissilva.trainerapp.domain.WorkoutStatus
import cl.alexissilva.trainerapp.framework.database.workout.WorkoutEntity
import cl.alexissilva.trainerapp.framework.database.workoutlog.WorkoutLogEntity
import java.time.LocalDateTime

object DummyData {
    val workout = Workout("id", "name")
    val workout2 = Workout("id2", "name2")
    val workout3 = Workout("id3", "name3")


    val workoutEntity = WorkoutEntity("id", "name", 1, emptyList())
    val workoutEntity2 = WorkoutEntity("id2", "name2", 2, emptyList())

    val exercise = Exercise("id1", "name1")
    val exercise2 = Exercise("id2", "name2")

    val workoutLog = WorkoutLog("id", "workoutId")
    val workoutLog2 = WorkoutLog("id2", "workoutId2")
    val workoutLog3 = WorkoutLog("id3", "workoutId3")


    val workoutLogEntity = WorkoutLogEntity(
        "id", "workoutId", WorkoutStatus.UNKNOWN, LocalDateTime.MIN, null, emptyList()
    )
    val workoutLogEntity2 = WorkoutLogEntity(
        "id2", "workoutId2", WorkoutStatus.UNKNOWN, LocalDateTime.MIN, null, emptyList()
    )


}