package cl.alexissilva.trainerapp.data

import cl.alexissilva.trainerapp.domain.Workout
import kotlinx.coroutines.flow.Flow

class WorkoutRepositoryImpl(
    private val localSource: LocalWorkoutSource,
    private val remoteSource: RemoteWorkoutSource,
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
}
