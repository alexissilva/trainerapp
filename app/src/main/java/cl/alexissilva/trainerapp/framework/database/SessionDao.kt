package cl.alexissilva.trainerapp.framework.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(session: SessionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sessions: List<SessionEntity>)

    @Delete
    suspend fun delete(session: SessionEntity)

    @Query("SELECT * FROM session")
    fun getAll(): Flow<List<SessionEntity>>

    @Query("SELECT * FROM session WHERE id = :sessionId")
    fun getSessionById(sessionId: String): Flow<SessionEntity?>
}