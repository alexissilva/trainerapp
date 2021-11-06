package cl.alexissilva.trainerapp.usecases

import cl.alexissilva.trainerapp.data.WorkoutRepository
import cl.alexissilva.trainerapp.domain.Workout
import cl.alexissilva.trainerapp.domain.WorkoutLog
import cl.alexissilva.trainerapp.domain.WorkoutStatus
import java.time.Clock
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

class UpdateWorkoutStatus @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val clock: Clock,
) {
    suspend operator fun invoke(workout: Workout, status: WorkoutStatus) {
        val log = WorkoutLog(
            UUID.randomUUID().toString(),
            workout.id,
            status,
            LocalDateTime.now(clock)
        )
        return workoutRepository.saveWorkoutLog(log)
    }
}