package cl.alexissilva.trainerapp.framework.database

import androidx.room.TypeConverter
import cl.alexissilva.trainerapp.domain.Exercise
import cl.alexissilva.trainerapp.domain.WorkoutStatus
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate

class WorkoutConverters {
    private val gson = Gson()

    @TypeConverter
    fun exercisesToJson(exercises: List<Exercise>): String {
        return gson.toJson(exercises)
    }

    @TypeConverter
    fun exercisesFromJson(json: String): List<Exercise> {
        val exercisesType = object : TypeToken<List<Exercise>>() {}.type
        return gson.fromJson(json, exercisesType) as List<Exercise>
    }

    @TypeConverter
    fun dateToString(date: LocalDate): String {
        return date.toString()
    }

    @TypeConverter
    fun dateFromString(dateString: String): LocalDate {
        return LocalDate.parse(dateString)
    }


    @TypeConverter
    fun statusToOrdinal(status: WorkoutStatus): Int {
        return status.ordinal
    }

    @TypeConverter
    fun statusFromOrdinal(ordinal: Int): WorkoutStatus {
        return WorkoutStatus.values()[ordinal]
    }

}