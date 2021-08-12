package cl.alexissilva.trainerapp.framework.database

import cl.alexissilva.trainerapp.domain.Session
import cl.alexissilva.trainerapp.domain.SessionStatus
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import java.time.LocalDate


class SessionMapTest {
    private lateinit var sessionMap: SessionMap

    @Before
    fun setUp() {
        sessionMap = SessionMap()
    }

    @Test
    fun mapSession_toSessionEntity() {
        val session = Session("id", "name", status = SessionStatus.SKIPPED)
        val entity = sessionMap.toSessionEntity(session)
        assertThat(entity).isInstanceOf(SessionEntity::class.java)
        assertThat(entity.id).isEqualTo(session.id)
        assertThat(entity.name).isEqualTo(session.name)
        assertThat(entity.name).isEqualTo(session.name)
        assertThat(entity.status).isEqualTo(session.status)


    }

    @Test
    fun createSession_fromSessionEntity() {
        val entity = SessionEntity("id", "name", LocalDate.MIN, emptyList(), status = SessionStatus.DONE)
        val session = sessionMap.fromSessionEntity(entity)
        assertThat(session).isInstanceOf(Session::class.java)
        assertThat(session.id).isEqualTo(entity.id)
        assertThat(session.name).isEqualTo(entity.name)
        assertThat(session.status).isEqualTo(entity.status)

    }
}