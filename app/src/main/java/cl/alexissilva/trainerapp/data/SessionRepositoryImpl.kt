package cl.alexissilva.trainerapp.data

import cl.alexissilva.trainerapp.domain.Session
import kotlinx.coroutines.flow.Flow

class SessionRepositoryImpl(
    private val localSource: LocalSessionSource,
    private val remoteSource: RemoteSessionSource,
) : SessionRepository {

    override suspend fun saveSession(session: Session) {
        return localSource.save(session)
    }

    override suspend fun saveSessions(sessions: List<Session>) {
        return localSource.save(sessions)
    }

    override suspend fun deleteSession(session: Session) {
        return localSource.delete(session)
    }

    override fun getLocalSessions(): Flow<List<Session>> {
        return localSource.getAll()
    }

    override fun getSessionById(sessionId: String): Flow<Session?> {
        return localSource.getSessionById(sessionId)
    }

    override suspend fun getRemoteSessions(): RemoteResult<List<Session>> {
        return remoteSource.getSessions()
    }
}
