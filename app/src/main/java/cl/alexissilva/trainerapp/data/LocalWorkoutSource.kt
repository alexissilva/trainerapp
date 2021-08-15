package cl.alexissilva.trainerapp.data

import cl.alexissilva.trainerapp.domain.Workout
import kotlinx.coroutines.flow.Flow

interface LocalWorkoutSource {

    suspend fun save(workout: Workout)

    suspend fun save(workouts: List<Workout>)

    suspend fun delete(workout: Workout)

    fun getAll(): Flow<List<Workout>>

    fun getWorkoutById(workoutId: String): Flow<Workout?>
}