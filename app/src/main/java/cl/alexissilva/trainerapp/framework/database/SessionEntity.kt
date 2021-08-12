package cl.alexissilva.trainerapp.framework.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import cl.alexissilva.trainerapp.domain.Exercise
import cl.alexissilva.trainerapp.domain.SessionStatus
import java.time.LocalDate

@Entity(tableName = "session")
data class SessionEntity(
    @PrimaryKey
    var id: String,
    var name: String,
    var date: LocalDate,
    var exercises: List<Exercise>,
    var status: SessionStatus,
)
