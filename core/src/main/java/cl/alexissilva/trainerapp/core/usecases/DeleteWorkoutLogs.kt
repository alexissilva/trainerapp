package cl.alexissilva.trainerapp.core.usecases

import cl.alexissilva.trainerapp.core.data.WorkoutRepository
import kotlinx.coroutines.flow.first

class DeleteWorkoutLogs(
    private val workoutRepository: WorkoutRepository
) {
    suspend operator fun invoke() {
        //TODO optimize -> delete all at once
        workoutRepository.getWorkoutLogs().first().forEach {
            workoutRepository.deleteWorkoutLog(it)
        }
    }
}