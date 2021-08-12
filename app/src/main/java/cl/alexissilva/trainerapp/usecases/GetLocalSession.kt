package cl.alexissilva.trainerapp.usecases

import cl.alexissilva.trainerapp.data.SessionRepository
import cl.alexissilva.trainerapp.domain.Session
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalSession @Inject constructor(
    private val sessionRepository: SessionRepository
) {
    operator fun invoke(sessionId: String): Flow<Session?> {
        return sessionRepository.getSessionById(sessionId)
    }
}