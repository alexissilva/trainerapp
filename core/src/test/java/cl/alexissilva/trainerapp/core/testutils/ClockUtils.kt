package cl.alexissilva.trainerapp.core.testutils

import java.time.Clock
import java.time.LocalDate
import java.time.ZoneId


fun fixedClock(localDate: LocalDate): Clock {
    return Clock.fixed(
        localDate.atStartOfDay(ZoneId.systemDefault()).toInstant(),
        ZoneId.systemDefault()
    )
}

