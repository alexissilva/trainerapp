package cl.alexissilva.trainerapp.usecases

import cl.alexissilva.trainerapp.domain.Workout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNextWorkout @Inject constructor(
    private val getUpcomingWorkouts: GetUpcomingWorkouts,
) {
    operator fun invoke(): Flow<Workout?> {
        //TODO decide what to do with workout without days
        return getUpcomingWorkouts().map { it.minByOrNull { workout -> workout.day ?: 0 } }
    }
}