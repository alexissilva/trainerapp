package cl.alexissilva.trainerapp.core.usecases

import cl.alexissilva.trainerapp.core.domain.ExerciseLog
import cl.alexissilva.trainerapp.core.domain.SetLog
import cl.alexissilva.trainerapp.core.domain.WorkoutLog
import cl.alexissilva.trainerapp.core.domain.WorkoutStatus
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDateTime
import java.util.*

class CreateDraftWorkoutLog(
    private val getLocalWorkout: GetLocalWorkout,
) {

    suspend operator fun invoke(workoutId: String): WorkoutLog? {
        val workout = getLocalWorkout(workoutId).firstOrNull() ?: return null
        val exerciseLogs = workout.exercises.map { wExercise ->
            val setLogs = mutableListOf<SetLog>()
            var countSets = 0
            wExercise.groupSets.forEach { gSet ->
                repeat(gSet.sets) {
                    countSets++
                    setLogs.add(SetLog(randomUUID(), countSets, true, gSet.reps))
                }
            }
            ExerciseLog(randomUUID(), wExercise.exercise, setLogs = setLogs)
        }
        return WorkoutLog(
            randomUUID(),
            workoutId,
            status = WorkoutStatus.DONE, //TODO where to define status? or replace for a simple boolean?
            exerciseLogs = exerciseLogs,
            date = LocalDateTime.now()
        )
    }


    private fun randomUUID(): String = UUID.randomUUID().toString()
}