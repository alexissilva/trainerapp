package cl.alexissilva.trainerapp.core.usecases

import cl.alexissilva.trainerapp.core.data.WorkoutRepository
import cl.alexissilva.trainerapp.core.domain.WorkoutLog
import kotlinx.coroutines.flow.Flow


class GetWorkoutLog(
    private val workoutRepository: WorkoutRepository
) {
    operator fun invoke(workoutLogId: String): Flow<WorkoutLog?> {
        return workoutRepository.getWorkoutLogById(workoutLogId)
    }
}