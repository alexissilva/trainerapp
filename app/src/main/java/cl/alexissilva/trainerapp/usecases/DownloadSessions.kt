package cl.alexissilva.trainerapp.usecases

import cl.alexissilva.trainerapp.data.RemoteResult
import cl.alexissilva.trainerapp.data.SessionRepository
import cl.alexissilva.trainerapp.domain.Session
import javax.inject.Inject

class DownloadSessions @Inject constructor(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(): RemoteResult<List<Session>> {
        val result = sessionRepository.getRemoteSessions()
        if (result is RemoteResult.Success) {
            sessionRepository.saveSessions(result.data)
        }
        return result
    }
}