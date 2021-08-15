package cl.alexissilva.trainerapp.usecases

import cl.alexissilva.trainerapp.data.WorkoutRepository
import cl.alexissilva.trainerapp.domain.Workout
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalWorkouts @Inject constructor(
    private val workoutRepository: WorkoutRepository
) {
    operator fun invoke(): Flow<List<Workout>> {
        return workoutRepository.getLocalWorkouts()
    }
}