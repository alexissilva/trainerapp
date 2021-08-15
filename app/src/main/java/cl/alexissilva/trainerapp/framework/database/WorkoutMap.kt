package cl.alexissilva.trainerapp.framework.database

import cl.alexissilva.trainerapp.domain.Workout

class WorkoutMap {

    fun toWorkoutEntity(workout: Workout): WorkoutEntity {
        return WorkoutEntity(workout.id, workout.name, workout.date, workout.exercises, workout.status)
    }

    fun fromWorkoutEntity(entity: WorkoutEntity): Workout {
        return Workout(entity.id, entity.name, entity.date, entity.exercises, entity.status)
    }
}