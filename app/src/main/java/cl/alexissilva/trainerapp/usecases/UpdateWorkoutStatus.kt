package cl.alexissilva.trainerapp.usecases

import cl.alexissilva.trainerapp.data.WorkoutRepository
import cl.alexissilva.trainerapp.domain.Workout
import cl.alexissilva.trainerapp.domain.WorkoutStatus
import javax.inject.Inject

class UpdateWorkoutStatus @Inject constructor(
    private val workoutRepository: WorkoutRepository
) {
    suspend operator fun invoke(workout: Workout, status: WorkoutStatus) {
        workout.status = status
        return workoutRepository.saveWorkout(workout)
    }
}