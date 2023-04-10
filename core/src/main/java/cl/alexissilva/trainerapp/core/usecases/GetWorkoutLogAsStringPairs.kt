package cl.alexissilva.trainerapp.core.usecases

import kotlinx.coroutines.flow.firstOrNull

class GetWorkoutLogAsStringPairs(
    private val getWorkoutLog: GetWorkoutLog
) {
    suspend operator fun invoke(workoutLogId: String): List<Pair<String, List<String>>> {
        val workoutLog = getWorkoutLog(workoutLogId).firstOrNull() ?: return emptyList()

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