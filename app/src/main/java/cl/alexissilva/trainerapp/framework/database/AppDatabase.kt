package cl.alexissilva.trainerapp.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [WorkoutEntity::class],
    version = 1,
)
@TypeConverters(WorkoutConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao
}