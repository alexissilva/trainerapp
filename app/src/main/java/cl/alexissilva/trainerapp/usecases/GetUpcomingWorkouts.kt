package cl.alexissilva.trainerapp.usecases

import cl.alexissilva.trainerapp.data.WorkoutRepository
import cl.alexissilva.trainerapp.domain.Workout
import cl.alexissilva.trainerapp.domain.WorkoutStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetUpcomingWorkouts @Inject constructor(
    private val workoutRepository: WorkoutRepository,
) {
    operator fun invoke(): Flow<List<Workout>> {
        //TODO optimize with Room relations
        // https://developer.android.com/training/data-storage/room/relationships
        val logsFlow = workoutRepository.getWorkoutLogs()
        val workoutsFlow = workoutRepository.getLocalWorkouts()
        return workoutsFlow.combine(logsFlow) { workouts, logs ->
            workouts.filter { workout ->
                val logOfWorkout = logs.filter { log -> log.workoutId == workout.id }
                if (logOfWorkout.isEmpty()) {
                    return@filter true
                }
                val unknownLogs = logOfWorkout.filter { it.status != WorkoutStatus.UNKNOWN }
                return@filter unknownLogs.isEmpty()
            }
        }
    }

}