package cl.alexissilva.trainerapp.usecases

import cl.alexissilva.trainerapp.data.WorkoutRepository
import cl.alexissilva.trainerapp.domain.WorkoutLog
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWorkoutLogsWithWorkout @Inject constructor(
    private val workoutRepository: WorkoutRepository
) {
    operator fun invoke(): Flow<List<WorkoutLog>> {
        return workoutRepository.getWorkoutLogsWithWorkout()
    }
}