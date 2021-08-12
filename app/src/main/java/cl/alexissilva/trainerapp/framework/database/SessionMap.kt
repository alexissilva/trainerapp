package cl.alexissilva.trainerapp.framework.database

import cl.alexissilva.trainerapp.domain.Session

class SessionMap {

    fun toSessionEntity(session: Session): SessionEntity {
        return SessionEntity(session.id, session.name, session.date, session.exercises, session.status)
    }

    fun fromSessionEntity(entity: SessionEntity): Session {
        return Session(entity.id, entity.name, entity.date, entity.exercises, entity.status)
    }
}