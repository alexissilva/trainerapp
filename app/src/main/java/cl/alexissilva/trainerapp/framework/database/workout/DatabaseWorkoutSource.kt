package cl.alexissilva.trainerapp.framework.database.workout

import cl.alexissilva.trainerapp.core.data.LocalWorkoutSource
import cl.alexissilva.trainerapp.core.domain.Workout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatabaseWorkoutSource(
    private val workoutDao: WorkoutDao,
    private val workoutMap: WorkoutMap,
) : LocalWorkoutSource {

    override suspend fun save(workout: Workout) {
        val entity = workoutMap.toWorkoutEntity(workout)
        workoutDao.insert(entity)
    }

    override suspend fun save(workouts: List<Workout>) {
        val entities = workouts.map { workoutMap.toWorkoutEntity(it) }
        workoutDao.insert(entities)
    }

    override suspend fun delete(workout: Workout) {
        val entity = workoutMap.toWorkoutEntity(workout)
        workoutDao.delete(entity)
    }

    override fun getAll(): Flow<List<Workout>> {
        return workoutDao.getAll()
            .map { list ->
                list.map { workout ->
                    workoutMap.fromWorkoutEntity(workout)
                }
            }
    }

    override fun getWorkoutById(workoutId: String): Flow<Workout?> {
        return workoutDao.getById(workoutId)
            .map {
                if (it != null) {
                    return@map workoutMap.fromWorkoutEntity(it)
                }
                null
            }
    }
}
