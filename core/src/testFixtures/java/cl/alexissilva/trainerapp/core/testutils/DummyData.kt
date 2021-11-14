package cl.alexissilva.trainerapp.core.testutils

import cl.alexissilva.trainerapp.core.domain.Exercise
import cl.alexissilva.trainerapp.core.domain.Workout
import cl.alexissilva.trainerapp.core.domain.WorkoutLog

object DummyData {
    val workout = Workout("id", "name")
    val workout2 = Workout("id2", "name2")
    val workout3 = Workout("id3", "name3")

    val exercise = Exercise("id1", "name1")
    val exercise2 = Exercise("id2", "name2")

    val workoutLog = WorkoutLog("id", "workoutId")
    val workoutLog2 = WorkoutLog("id2", "workoutId2")
    val workoutLog3 = WorkoutLog("id3", "workoutId3")
}