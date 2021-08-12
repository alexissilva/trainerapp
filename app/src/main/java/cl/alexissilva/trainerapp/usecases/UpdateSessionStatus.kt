package cl.alexissilva.trainerapp.usecases

import cl.alexissilva.trainerapp.data.SessionRepository
import cl.alexissilva.trainerapp.domain.Session
import cl.alexissilva.trainerapp.domain.SessionStatus
import javax.inject.Inject

class UpdateSessionStatus @Inject constructor(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(session: Session, status: SessionStatus) {
        session.status = status
        return sessionRepository.saveSession(session)
    }
}