package cl.alexissilva.trainerapp.usecases

import cl.alexissilva.trainerapp.data.SessionRepository
import cl.alexissilva.trainerapp.domain.Session
import cl.alexissilva.trainerapp.domain.SessionStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPastSessions @Inject constructor(
    private val sessionRepository: SessionRepository
) {
    operator fun invoke(): Flow<List<Session>> {
        return sessionRepository.getLocalSessions().map { sessions ->
            sessions.filter { it.status == SessionStatus.DONE || it.status == SessionStatus.SKIPPED }
        }
    }
}