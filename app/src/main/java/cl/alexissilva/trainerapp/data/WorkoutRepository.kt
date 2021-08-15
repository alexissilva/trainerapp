package cl.alexissilva.trainerapp.data

import cl.alexissilva.trainerapp.domain.Workout
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {

    suspend fun saveWorkout(workout: Workout)

    suspend fun saveWorkouts(workouts: List<Workout>)

    suspend fun deleteWorkout(workout: Workout)

    fun getLocalWorkouts(): Flow<List<Workout>>

    fun getWorkoutById(workoutId: String): Flow<Workout?>

    suspend fun getRemoteWorkouts(): RemoteResult<List<Workout>>
}