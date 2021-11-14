package cl.alexissilva.trainerapp.core.usecases

import cl.alexissilva.trainerapp.core.domain.Workout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNextWorkout(
    private val getUpcomingWorkouts: GetUpcomingWorkouts,
) {
    operator fun invoke(): Flow<Workout?> {
        //TODO decide what to do with workout without days
        return getUpcomingWorkouts().map { it.minByOrNull { workout -> workout.day ?: 0 } }
    }
}