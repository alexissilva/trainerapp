package cl.alexissilva.trainerapp.ui.showworkoutlog

import cl.alexissilva.trainerapp.core.domain.WorkoutLog

object WorkoutLogMapper {

    fun mapWorkoutLogToExerciseStrings(workoutLog: WorkoutLog): List<Pair<String, List<String>>> {
        return workoutLog.exerciseLogs.map { log ->
            val name = log.exercise.name
            val setLogs = mutableListOf<String>()
            var sets = 0
            var reps: Int? = null
            var weight: Int? = null
            log.setLogs.forEach { set ->
                if (sets == 0 || reps != set.repsDone || weight != set.weightUsed) {
                    formatSetLog(sets, reps, weight)?.let { setLogs.add(it) }
                    sets = 1
                    reps = set.repsDone
                    weight = set.weightUsed
                } else {
                    sets++
                }
            }
            formatSetLog(sets, reps, weight)?.let { setLogs.add(it) }
            Pair(name, setLogs)
        }
    }

    private fun formatSetLog(sets: Int, reps: Int?, weight: Int?): String? {
        if (sets <= 0) return null

        var setString = "$sets"
        if (reps != null && reps > 0) {
            setString += "x$reps"
        }
        if (weight != null && weight > 0) {
            setString += "@$weight"
        }

        return setString
    }
}