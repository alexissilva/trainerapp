package cl.alexissilva.trainerapp.core.data

import cl.alexissilva.trainerapp.core.domain.Workout
import cl.alexissilva.trainerapp.core.domain.WorkoutLog
import kotlinx.coroutines.flow.Flow

class WorkoutRepositoryImpl(
    private val localSource: LocalWorkoutSource,
    private val remoteSource: RemoteWorkoutSource,
    private val workoutLogSource: WorkoutLogSource,
) : WorkoutRepository {

    override suspend fun saveWorkout(workout: Workout) {
        return localSource.save(workout)
    }

    override suspend fun saveWorkouts(workouts: List<Workout>) {
        return localSource.save(workouts)
    }

    override suspend fun deleteWorkout(workout: Workout) {
        return localSource.delete(workout)
    }

    override fun getLocalWorkouts(): Flow<List<Workout>> {
        return localSource.getAll()
    }

    override fun getWorkoutById(workoutId: String): Flow<Workout?> {
        return localSource.getWorkoutById(workoutId)
    }

    override suspend fun getRemoteWorkouts(): RemoteResult<List<Workout>> {
        return remoteSource.getWorkouts()
    }

    override suspend fun saveWorkoutLog(workoutLog: WorkoutLog) {
        return workoutLogSource.saveWorkoutLog(workoutLog)
    }

    override suspend fun deleteWorkoutLog(workoutLog: WorkoutLog) {
        return workoutLogSource.deleteWorkoutLog(workoutLog)
    }

    override fun getWorkoutLogs(): Flow<List<WorkoutLog>> {
        return workoutLogSource.getWorkoutLogs()
    }

    override fun getWorkoutLogById(workoutLogId: String): Flow<WorkoutLog?> {
        return workoutLogSource.getWorkoutLogById(workoutLogId)
    }

    override fun getWorkoutLogByWorkoutId(workoutId: String): Flow<WorkoutLog?> {
        return workoutLogSource.getWorkoutLogByWorkoutId(workoutId)
    }

    override fun getWorkoutLogsWithWorkout(): Flow<List<WorkoutLog>> {
        return workoutLogSource.getWorkoutLogsWithWorkout()
    }
}
