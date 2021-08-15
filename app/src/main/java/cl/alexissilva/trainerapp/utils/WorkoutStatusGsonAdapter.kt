package cl.alexissilva.trainerapp.utils

import cl.alexissilva.trainerapp.domain.WorkoutStatus
import com.google.gson.*
import java.lang.reflect.Type

class WorkoutStatusGsonAdapter : JsonSerializer<WorkoutStatus>, JsonDeserializer<WorkoutStatus> {
    override fun serialize(
        status: WorkoutStatus,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(status.ordinal)
    }

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): WorkoutStatus {
        return WorkoutStatus.values()[json.asInt]
    }
}