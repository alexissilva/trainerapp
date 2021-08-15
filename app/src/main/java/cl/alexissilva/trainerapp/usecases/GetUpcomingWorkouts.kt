package cl.alexissilva.trainerapp.usecases

import cl.alexissilva.trainerapp.data.WorkoutRepository
import cl.alexissilva.trainerapp.domain.Workout
import cl.alexissilva.trainerapp.domain.WorkoutStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject

class GetUpcomingWorkouts @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val clock: Clock,
) {

    operator fun invoke(): Flow<List<Workout>> {
        val currentDate = LocalDate.now(clock)
        return workoutRepository.getLocalWorkouts().map { list ->
            list.filter { it.date >= currentDate && it.status == WorkoutStatus.PENDING }
        }
    }

}