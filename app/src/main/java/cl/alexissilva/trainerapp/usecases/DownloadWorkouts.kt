package cl.alexissilva.trainerapp.usecases

import cl.alexissilva.trainerapp.data.RemoteResult
import cl.alexissilva.trainerapp.data.WorkoutRepository
import cl.alexissilva.trainerapp.domain.Workout
import javax.inject.Inject

class DownloadWorkouts @Inject constructor(
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