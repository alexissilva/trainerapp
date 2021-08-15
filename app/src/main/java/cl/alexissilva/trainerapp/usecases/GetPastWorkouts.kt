package cl.alexissilva.trainerapp.usecases

import cl.alexissilva.trainerapp.data.WorkoutRepository
import cl.alexissilva.trainerapp.domain.Workout
import cl.alexissilva.trainerapp.domain.WorkoutStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPastWorkouts @Inject constructor(
    private val workoutRepository: WorkoutRepository
) {
    operator fun invoke(): Flow<List<Workout>> {
        return workoutRepository.getLocalWorkouts().map { workouts ->
            workouts.filter { it.status == WorkoutStatus.DONE || it.status == WorkoutStatus.SKIPPED }
        }
    }
}