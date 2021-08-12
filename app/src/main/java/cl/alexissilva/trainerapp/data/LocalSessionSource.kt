package cl.alexissilva.trainerapp.data

import cl.alexissilva.trainerapp.domain.Session
import kotlinx.coroutines.flow.Flow

interface LocalSessionSource {

    suspend fun save(session: Session)

    suspend fun save(sessions: List<Session>)

    suspend fun delete(session: Session)

    fun getAll(): Flow<List<Session>>

    fun getSessionById(sessionId: String) : Flow<Session?>
}