package cl.alexissilva.trainerapp.usecases

import cl.alexissilva.trainerapp.data.WorkoutRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DeleteWorkoutLogs @Inject constructor(
    private val workoutRepository: WorkoutRepository
) {
    suspend operator fun invoke() {
        //TODO optimize -> delete all at once
        workoutRepository.getWorkoutLogs().first().forEach {
            workoutRepository.deleteWorkoutLog(it)
        }
    }
}