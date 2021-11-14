package cl.alexissilva.trainerapp.core.data

import cl.alexissilva.trainerapp.core.domain.WorkoutLog
import kotlinx.coroutines.flow.Flow

interface WorkoutLogSource {

    suspend fun saveWorkoutLog(workoutLog: WorkoutLog)

    suspend fun deleteWorkoutLog(workoutLog: WorkoutLog)

    fun getWorkoutLogs(): Flow<List<WorkoutLog>>

    fun getWorkoutLogById(workoutLogId: String): Flow<WorkoutLog?>

    fun getWorkoutLogByWorkoutId(workoutId: String): Flow<WorkoutLog?>

    fun getWorkoutLogsWithWorkout(): Flow<List<WorkoutLog>>
}