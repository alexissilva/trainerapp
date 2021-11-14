package cl.alexissilva.trainerapp.framework.database.workoutlog

import cl.alexissilva.trainerapp.core.domain.WorkoutLog
import cl.alexissilva.trainerapp.framework.database.workout.WorkoutMap

class WorkoutLogMap {

    private val workoutMap = WorkoutMap()

    fun toWorkoutLogEntity(workoutLog: WorkoutLog): WorkoutLogEntity {
        return WorkoutLogEntity(
            workoutLog.id,
            workoutLog.workoutId,
            workoutLog.status,
            workoutLog.date,
            workoutLog.comment,
            workoutLog.exerciseLogs,
        )
    }

    fun fromWorkoutLogEntity(entity: WorkoutLogEntity): WorkoutLog {
        return WorkoutLog(
            entity.id,
            entity.workoutId,
            entity.status,
            entity.date,
            entity.comment,
            entity.exerciseLogs,
        )
    }

    fun fromWorkoutLogWithWorkout(logWithWorkout: WorkoutLogWithWorkout): WorkoutLog {
        return WorkoutLog(
            logWithWorkout.workoutLog.id,
            logWithWorkout.workoutLog.workoutId,
            logWithWorkout.workoutLog.status,
            logWithWorkout.workoutLog.date,
            logWithWorkout.workoutLog.comment,
            logWithWorkout.workoutLog.exerciseLogs,
            workoutMap.fromWorkoutEntity(logWithWorkout.workout),
        )
    }
}