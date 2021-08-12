package cl.alexissilva.trainerapp.framework.database

import cl.alexissilva.trainerapp.data.LocalSessionSource
import cl.alexissilva.trainerapp.domain.Session
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatabaseSessionSource(
    private val sessionDao: SessionDao,
    private val sessionMap: SessionMap,
) : LocalSessionSource {


    override suspend fun save(session: Session) {
        val entity = sessionMap.toSessionEntity(session)
        sessionDao.insert(entity)
    }

    override suspend fun save(sessions: List<Session>) {
        val entities = sessions.map { sessionMap.toSessionEntity(it) }
        sessionDao.insert(entities)
    }

    override suspend fun delete(session: Session) {
        val entity = sessionMap.toSessionEntity(session)
        sessionDao.delete(entity)
    }

    override fun getAll(): Flow<List<Session>> {
        return sessionDao.getAll()
            .map { list ->
                list.map { session ->
                    sessionMap.fromSessionEntity(session)
                }
            }
    }

    override fun getSessionById(sessionId: String): Flow<Session?> {
        return sessionDao.getSessionById(sessionId)
            .map {
                if (it != null) {
                    return@map sessionMap.fromSessionEntity(it)
                }
                null
            }
    }


}
