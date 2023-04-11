package cl.alexissilva.trainerapp.framework.database.workout

import cl.alexissilva.trainerapp.core.domain.Workout
import cl.alexissilva.trainerapp.core.testutils.DummyData
import cl.alexissilva.trainerapp.testutils.AppDummyData
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class WorkoutMapTest {

    private val workout = DummyData.workout
    private val workoutEntity = AppDummyData.workoutEntity
    private lateinit var workoutMap: WorkoutMap

    @Before
    fun setUp() {
        workoutMap = WorkoutMap()
    }

    @Test
    fun mapWorkout_toWorkoutEntity() {
        val entity = workoutMap.toWorkoutEntity(workout)
        assertThat(entity).isInstanceOf(WorkoutEntity::class.java)
        assertThatAreEquivalent(workout, entity)
    }

    @Test
    fun createWorkout_fromWorkoutEntity() {
        val workout = workoutMap.fromWorkoutEntity(workoutEntity)
        assertThat(workout).isInstanceOf(Workout::class.java)
        assertThatAreEquivalent(workout, workoutEntity)
    }

    private fun assertThatAreEquivalent(workout: Workout, entity: WorkoutEntity) {
        assertThat(workout.id).isEqualTo(entity.id)
        assertThat(workout.name).isEqualTo(entity.name)
        assertThat(workout.day).isEqualTo(entity.day)
    }
}