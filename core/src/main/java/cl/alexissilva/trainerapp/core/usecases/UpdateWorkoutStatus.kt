package cl.alexissilva.trainerapp.core.usecases

import cl.alexissilva.trainerapp.core.data.WorkoutRepository
import cl.alexissilva.trainerapp.core.domain.Workout
import cl.alexissilva.trainerapp.core.domain.WorkoutLog
import cl.alexissilva.trainerapp.core.domain.WorkoutStatus
import java.time.Clock
import java.time.LocalDateTime
import java.util.*

class UpdateWorkoutStatus(
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