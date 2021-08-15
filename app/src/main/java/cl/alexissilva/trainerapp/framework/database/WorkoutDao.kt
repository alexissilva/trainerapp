package cl.alexissilva.trainerapp.framework.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(workout: WorkoutEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(workouts: List<WorkoutEntity>)

    @Delete
    suspend fun delete(workout: WorkoutEntity)

    @Query("SELECT * FROM workout")
    fun getAll(): Flow<List<WorkoutEntity>>

    @Query("SELECT * FROM workout WHERE id = :workoutId")
    fun getById(workoutId: String): Flow<WorkoutEntity?>
}