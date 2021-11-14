package cl.alexissilva.trainerapp.framework.database.workoutlog

import androidx.room.TypeConverter
import cl.alexissilva.trainerapp.core.domain.ExerciseLog
import cl.alexissilva.trainerapp.core.domain.WorkoutStatus
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDateTime

class WorkoutLogConverters {
    private val gson = Gson()

    @TypeConverter
    fun statusToOrdinal(status: WorkoutStatus): Int {
        return status.ordinal
    }

    @TypeConverter
    fun statusFromOrdinal(ordinal: Int): WorkoutStatus {
        return WorkoutStatus.values()[ordinal]
    }


    @TypeConverter
    fun exerciseLogsToJson(exercises: List<ExerciseLog>): String {
        return gson.toJson(exercises)
    }

    @TypeConverter
    fun exerciseLogsFromJson(json: String): List<ExerciseLog> {
        val type = object : TypeToken<List<ExerciseLog>>() {}.type
        return gson.fromJson(json, type) as List<ExerciseLog>
    }

    @TypeConverter
    fun dateTimeToString(dateTime: LocalDateTime): String {
        return dateTime.toString()
    }

    @TypeConverter
    fun dateTimeFromString(dateTimeString: String): LocalDateTime {
        return LocalDateTime.parse(dateTimeString)
    }


}