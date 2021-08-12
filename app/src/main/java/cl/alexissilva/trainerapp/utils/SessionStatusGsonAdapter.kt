package cl.alexissilva.trainerapp.utils

import cl.alexissilva.trainerapp.domain.SessionStatus
import com.google.gson.*
import java.lang.reflect.Type

//TODO create dto object for network(?)
class SessionStatusGsonAdapter : JsonSerializer<SessionStatus>, JsonDeserializer<SessionStatus> {
    override fun serialize(
        status: SessionStatus,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(status.ordinal)
    }

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): SessionStatus {
        return SessionStatus.values()[json.asInt]
    }
}