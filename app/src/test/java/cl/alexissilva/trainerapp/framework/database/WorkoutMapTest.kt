package cl.alexissilva.trainerapp.framework.database

import cl.alexissilva.trainerapp.domain.Workout
import cl.alexissilva.trainerapp.domain.WorkoutStatus
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import java.time.LocalDate


class WorkoutMapTest {
    private lateinit var workoutMap: WorkoutMap

    @Before
    fun setUp() {
        workoutMap = WorkoutMap()
    }

    @Test
    fun mapWorkout_toWorkoutEntity() {
        val workout = Workout("id", "name", status = WorkoutStatus.SKIPPED)
        val entity = workoutMap.toWorkoutEntity(workout)
        assertThat(entity).isInstanceOf(WorkoutEntity::class.java)
        assertThat(entity.id).isEqualTo(workout.id)
        assertThat(entity.name).isEqualTo(workout.name)
        assertThat(entity.name).isEqualTo(workout.name)
        assertThat(entity.status).isEqualTo(workout.status)


    }

    @Test
    fun createWorkout_fromWorkoutEntity() {
        val entity = WorkoutEntity("id", "name", LocalDate.MIN, emptyList(), status = WorkoutStatus.DONE)
        val workout = workoutMap.fromWorkoutEntity(entity)
        assertThat(workout).isInstanceOf(Workout::class.java)
        assertThat(workout.id).isEqualTo(entity.id)
        assertThat(workout.name).isEqualTo(entity.name)
        assertThat(workout.status).isEqualTo(entity.status)

    }
}