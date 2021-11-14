package cl.alexissilva.trainerapp.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cl.alexissilva.trainerapp.core.domain.WorkoutLog
import cl.alexissilva.trainerapp.framework.database.workout.WorkoutConverters
import cl.alexissilva.trainerapp.framework.database.workout.WorkoutDao
import cl.alexissilva.trainerapp.framework.database.workout.WorkoutEntity
import cl.alexissilva.trainerapp.framework.database.workoutlog.WorkoutLogConverters
import cl.alexissilva.trainerapp.framework.database.workoutlog.WorkoutLogDao
import cl.alexissilva.trainerapp.framework.database.workoutlog.WorkoutLogEntity

@Database(
    entities = [WorkoutEntity::class, WorkoutLogEntity::class],
    version = 1,
)
@TypeConverters(value = [WorkoutConverters::class, WorkoutLogConverters::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao

    abstract fun workoutLogDao(): WorkoutLogDao
}