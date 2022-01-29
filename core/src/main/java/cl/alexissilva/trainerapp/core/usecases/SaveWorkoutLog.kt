package cl.alexissilva.trainerapp.core.usecases

import cl.alexissilva.trainerapp.core.data.WorkoutRepository
import cl.alexissilva.trainerapp.core.domain.WorkoutLog
import java.time.Clock
import java.time.LocalDateTime

class SaveWorkoutLog(
    private val workoutRepository: WorkoutRepository,
    private val clock: Clock,
) {
    suspend operator fun invoke(workoutLog: WorkoutLog) {
        //TODO define where/how to set date
        val log = workoutLog.copy(date = LocalDateTime.now(clock))
        return workoutRepository.saveWorkoutLog(log)
    }
}