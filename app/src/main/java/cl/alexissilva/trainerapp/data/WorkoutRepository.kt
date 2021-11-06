package cl.alexissilva.trainerapp.data

import cl.alexissilva.trainerapp.domain.Workout
import cl.alexissilva.trainerapp.domain.WorkoutLog
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {

    suspend fun saveWorkout(workout: Workout)

    suspend fun saveWorkouts(workouts: List<Workout>)

    suspend fun deleteWorkout(workout: Workout)

    fun getLocalWorkouts(): Flow<List<Workout>>

    fun getWorkoutById(workoutId: String): Flow<Workout?>

    suspend fun getRemoteWorkouts(): RemoteResult<List<Workout>>

    /**
     * Workout Logs
     */

    suspend fun saveWorkoutLog(workoutLog: WorkoutLog)

    suspend fun deleteWorkoutLog(workoutLog: WorkoutLog)

    fun getWorkoutLogs(): Flow<List<WorkoutLog>>

    fun getWorkoutLogById(workoutLogId: String): Flow<WorkoutLog?>

    fun getWorkoutLogByWorkoutId(workoutId: String): Flow<WorkoutLog?>

    fun getWorkoutLogsWithWorkout(): Flow<List<WorkoutLog>>
}