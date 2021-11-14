package cl.alexissilva.trainerapp.core.data

import cl.alexissilva.trainerapp.core.domain.Workout
import kotlinx.coroutines.flow.Flow

interface LocalWorkoutSource {

    suspend fun save(workout: Workout)

    suspend fun save(workouts: List<Workout>)

    suspend fun delete(workout: Workout)

    fun getAll(): Flow<List<Workout>>

    fun getWorkoutById(workoutId: String): Flow<Workout?>
}