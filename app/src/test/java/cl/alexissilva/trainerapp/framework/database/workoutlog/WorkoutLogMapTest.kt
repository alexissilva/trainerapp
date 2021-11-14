package cl.alexissilva.trainerapp.framework.database.workoutlog

import cl.alexissilva.trainerapp.core.domain.WorkoutLog
import cl.alexissilva.trainerapp.core.testutils.DummyData
import cl.alexissilva.trainerapp.testutils.AppDummyData
import com.google.common.truth.Truth.assertThat
import org.junit.Before

import org.junit.Test

class WorkoutLogMapTest {
    private lateinit var map: WorkoutLogMap
    private val log = DummyData.workoutLog
    private val logEntity = AppDummyData.workoutLogEntity

    @Before
    fun setUp() {
        map = WorkoutLogMap()
    }

    @Test
    fun mapLog_toWorkoutLogEntity() {
        val entity = map.toWorkoutLogEntity(log)
        assertThatAreEquivalent(log, entity)
    }

    @Test
    fun mapLog_fromWorkoutLogEntity() {
        val log = map.fromWorkoutLogEntity(logEntity)
        assertThatAreEquivalent(log, logEntity)
    }

    private fun assertThatAreEquivalent(log: WorkoutLog, entity: WorkoutLogEntity) {
        assertThat(log.id).isEqualTo(entity.id)
        assertThat(log.workoutId).isEqualTo(entity.workoutId)
        assertThat(log.status).isEqualTo(entity.status)
        assertThat(log.date).isEqualTo(entity.date)
        assertThat(log.comment).isEqualTo(entity.comment)
        assertThat(log.exerciseLogs).isEqualTo(entity.exerciseLogs)

    }

}