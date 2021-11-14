package cl.alexissilva.trainerapp.framework.database.workout

import androidx.room.TypeConverter
import cl.alexissilva.trainerapp.core.domain.WorkoutExercise
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate

class WorkoutConverters {
    private val gson = Gson()

    @TypeConverter
    fun exercisesToJson(exercises: List<WorkoutExercise>): String {
        return gson.toJson(exercises)
    }

    @TypeConverter
    fun exercisesFromJson(json: String): List<WorkoutExercise> {
        val exercisesType = object : TypeToken<List<WorkoutExercise>>() {}.type
        return gson.fromJson(json, exercisesType) as List<WorkoutExercise>
    }

    @TypeConverter
    fun dateToString(date: LocalDate): String {
        return date.toString()
    }

    @TypeConverter
    fun dateFromString(dateString: String): LocalDate {
        return LocalDate.parse(dateString)
    }

}