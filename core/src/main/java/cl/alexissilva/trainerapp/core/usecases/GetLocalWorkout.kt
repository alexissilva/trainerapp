package cl.alexissilva.trainerapp.core.usecases

import cl.alexissilva.trainerapp.core.data.WorkoutRepository
import cl.alexissilva.trainerapp.core.domain.Workout
import kotlinx.coroutines.flow.Flow

class GetLocalWorkout(
    private val workoutRepository: WorkoutRepository
) {
    operator fun invoke(workoutId: String): Flow<Workout?> {
        return workoutRepository.getWorkoutById(workoutId)
    }
}