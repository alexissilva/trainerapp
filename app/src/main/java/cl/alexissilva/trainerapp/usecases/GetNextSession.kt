package cl.alexissilva.trainerapp.usecases

import cl.alexissilva.trainerapp.data.SessionRepository
import cl.alexissilva.trainerapp.domain.Session
import cl.alexissilva.trainerapp.domain.SessionStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject

class GetNextSession @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val clock: Clock,
) {

    operator fun invoke(): Flow<Session?> {
        val currentDate = LocalDate.now(clock)
        return sessionRepository.getLocalSessions().map { list ->
            list.filter { it.date >= currentDate && it.status == SessionStatus.PENDING }
                .minByOrNull { it.date }
        }
    }

}