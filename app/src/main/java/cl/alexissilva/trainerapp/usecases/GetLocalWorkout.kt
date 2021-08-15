package cl.alexissilva.trainerapp.usecases

import cl.alexissilva.trainerapp.data.WorkoutRepository
import cl.alexissilva.trainerapp.domain.Workout
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalWorkout @Inject constructor(
    private val workoutRepository: WorkoutRepository
) {
    operator fun invoke(workoutId: String): Flow<Workout?> {
        return workoutRepository.getWorkoutById(workoutId)
    }
}