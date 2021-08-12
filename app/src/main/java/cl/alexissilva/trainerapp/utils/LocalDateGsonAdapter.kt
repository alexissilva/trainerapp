package cl.alexissilva.trainerapp.utils

import com.google.gson.*
import java.lang.reflect.Type
import java.time.LocalDate

class LocalDateGsonAdapter : JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
    override fun serialize(
        date: LocalDate,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(date.toString())
    }

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDate {
        return LocalDate.parse(json.asString)
    }
}