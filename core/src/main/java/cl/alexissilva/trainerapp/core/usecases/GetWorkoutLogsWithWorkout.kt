package cl.alexissilva.trainerapp.core.usecases

import cl.alexissilva.trainerapp.core.data.WorkoutRepository
import cl.alexissilva.trainerapp.core.domain.WorkoutLog
import kotlinx.coroutines.flow.Flow

class GetWorkoutLogsWithWorkout(
    private val workoutRepository: WorkoutRepository
) {
    operator fun invoke(): Flow<List<WorkoutLog>> {
        return workoutRepository.getWorkoutLogsWithWorkout()
    }
}