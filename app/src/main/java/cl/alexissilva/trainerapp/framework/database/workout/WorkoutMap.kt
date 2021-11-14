package cl.alexissilva.trainerapp.framework.database.workout

import cl.alexissilva.trainerapp.core.domain.Workout

class WorkoutMap {

    fun toWorkoutEntity(workout: Workout): WorkoutEntity {
        return WorkoutEntity(workout.id, workout.name, workout.day, workout.exercises)
    }

    fun fromWorkoutEntity(entity: WorkoutEntity): Workout {
        return Workout(entity.id, entity.name, entity.day, entity.exercises)
    }
}