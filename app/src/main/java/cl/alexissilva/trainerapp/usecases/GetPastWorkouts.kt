package cl.alexissilva.trainerapp.usecases

import cl.alexissilva.trainerapp.data.WorkoutRepository
import cl.alexissilva.trainerapp.domain.Workout
import cl.alexissilva.trainerapp.domain.WorkoutStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@Deprecated("Use GetWorkoutLogs instead")
class GetPastWorkouts @Inject constructor(
    private val workoutRepository: WorkoutRepository
) {
    operator fun invoke(): Flow<List<Workout>> {
        //TODO optimize(?)
        val logsFlow = workoutRepository.getWorkoutLogs()
        val workoutsFlow = workoutRepository.getLocalWorkouts()
        return workoutsFlow.combine(logsFlow) { workouts, logs ->
            workouts.filter { workout ->
                logs.find { log ->
                    log.workoutId == workout.id && log.status != WorkoutStatus.UNKNOWN
                } != null
            }

        }
    }
}