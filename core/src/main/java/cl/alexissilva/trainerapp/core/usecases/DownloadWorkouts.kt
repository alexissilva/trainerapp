package cl.alexissilva.trainerapp.core.usecases

import cl.alexissilva.trainerapp.core.data.RemoteResult
import cl.alexissilva.trainerapp.core.data.WorkoutRepository
import cl.alexissilva.trainerapp.core.domain.Workout

class DownloadWorkouts(
    private val workoutRepository: WorkoutRepository
) {
    suspend operator fun invoke(): RemoteResult<List<Workout>> {
        val result = workoutRepository.getRemoteWorkouts()
        if (result is RemoteResult.Success) {
            workoutRepository.saveWorkouts(result.data)
        }
        return result
    }
}