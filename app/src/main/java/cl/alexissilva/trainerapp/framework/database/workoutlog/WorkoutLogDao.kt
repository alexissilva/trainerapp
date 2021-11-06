package cl.alexissilva.trainerapp.framework.database.workoutlog

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(workoutLogEntity: WorkoutLogEntity)

    @Delete
    suspend fun delete(workoutLogEntity: WorkoutLogEntity)

    @Query("SELECT * FROM workout_log")
    fun getAll(): Flow<List<WorkoutLogEntity>>

    @Query("SELECT * FROM workout_log WHERE id = :id")
    fun getById(id: String): Flow<WorkoutLogEntity?>

    @Query("SELECT * FROM workout_log WHERE workoutId = :workoutId ORDER BY date LIMIT 1")
    fun getLastByWorkoutId(workoutId: String): Flow<WorkoutLogEntity?>

    @Transaction
    @Query("SELECT * FROM workout_log")
    fun getAllLogsWithWorkout(): Flow<List<WorkoutLogWithWorkout>>
}