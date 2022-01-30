package cl.alexissilva.trainerapp.ui.adapters.exerciselogs

import cl.alexissilva.trainerapp.core.domain.ExerciseLog
import cl.alexissilva.trainerapp.core.domain.SetLog

object ExerciseLogItemMapper {

    fun mapLogsToItems(exerciseLogs: List<ExerciseLog>, readOnly: Boolean): List<ExerciseLogItem> {
        val items = mutableListOf<ExerciseLogItem>()
        exerciseLogs.forEach { exerciseLog ->
            items.add(ExerciseHeader(exerciseLog.id, exerciseLog.exercise))
            exerciseLog.setLogs.forEach { setLog ->
                items.add(
                    SetLogItem(
                        setLog.id,
                        setLog.number,
                        setLog.done,
                        setLog.repsDone,
                        setLog.weightUsed,
                        readOnly
                    )
                )
            }
        }
        return items
    }

    fun mapItemsToLogs(items: List<ExerciseLogItem>): List<ExerciseLog> {
        val logs = mutableListOf<ExerciseLog>()
        var log: ExerciseLog? = null
        var sets = mutableListOf<SetLog>()
        items.forEach { item ->
            when (item) {
                is ExerciseHeader -> {
                    log?.let {
                        it.setLogs = sets
                        logs.add(it)
                        sets = mutableListOf()
                    }
                    log = ExerciseLog(item.exerciseLogId, item.exercise, item.comment)
                }
                is SetLogItem -> {
                    //TODO I'm not sure about if we need this "cleaning"
                    val reps = if (item.done) item.reps else null
                    val weight = if (item.done) item.weight else null
                    sets.add(SetLog(item.setLogId, item.number, item.done, reps, weight))
                }
            }
        }
        log?.let {
            it.setLogs = sets
            logs.add(it)
        }
        return logs
    }

}