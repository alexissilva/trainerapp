package cl.alexissilva.trainerapp.data

import cl.alexissilva.trainerapp.domain.Session
import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    suspend fun saveSession(session: Session)

    suspend fun saveSessions(sessions: List<Session>)

    suspend fun deleteSession(session: Session)

    fun getLocalSessions(): Flow<List<Session>>

    fun getSessionById(sessionId: String): Flow<Session?>

    suspend fun getRemoteSessions(): RemoteResult<List<Session>>
}