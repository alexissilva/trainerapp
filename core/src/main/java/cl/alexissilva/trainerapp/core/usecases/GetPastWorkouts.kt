package cl.alexissilva.trainerapp.core.usecases

import cl.alexissilva.trainerapp.core.data.WorkoutRepository
import cl.alexissilva.trainerapp.core.domain.Workout
import cl.alexissilva.trainerapp.core.domain.WorkoutStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

@Deprecated("Use GetWorkoutLogs instead")
class GetPastWorkouts(
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