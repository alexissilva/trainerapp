package cl.alexissilva.trainerapp.framework.database.workoutlog

import cl.alexissilva.trainerapp.core.data.WorkoutLogSource
import cl.alexissilva.trainerapp.core.domain.WorkoutLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatabaseWorkoutLogSource(
    private val dao: WorkoutLogDao,
    private val map: WorkoutLogMap,
) : WorkoutLogSource {

    override suspend fun saveWorkoutLog(workoutLog: WorkoutLog) {
        dao.save(map.toWorkoutLogEntity(workoutLog))
    }

    override suspend fun deleteWorkoutLog(workoutLog: WorkoutLog) {
        dao.delete(map.toWorkoutLogEntity(workoutLog))
    }

    override fun getWorkoutLogs(): Flow<List<WorkoutLog>> {
        return dao.getAll().map { logs ->
            logs.map { map.fromWorkoutLogEntity(it) }
        }
    }

    override fun getWorkoutLogById(workoutLogId: String): Flow<WorkoutLog?> {
        return dao.getById(workoutLogId).map { log ->
            log?.let { map.fromWorkoutLogEntity(it) }
                ?: run { null }
        }
    }

    override fun getWorkoutLogByWorkoutId(workoutId: String): Flow<WorkoutLog?> {
        return dao.getLastByWorkoutId(workoutId).map { log ->
            log?.let { map.fromWorkoutLogEntity(it) }
                ?: run { null }
        }
    }

    override fun getWorkoutLogsWithWorkout(): Flow<List<WorkoutLog>> {
        return dao.getAllLogsWithWorkout().map { logs ->
            logs.map { map.fromWorkoutLogWithWorkout(it) }
        }
    }
}