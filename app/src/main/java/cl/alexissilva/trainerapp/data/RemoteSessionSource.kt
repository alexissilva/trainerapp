package cl.alexissilva.trainerapp.data

import cl.alexissilva.trainerapp.domain.Session
import java.lang.Exception

interface RemoteSessionSource {
    suspend fun getSessions(): RemoteResult<List<Session>>
}

sealed class RemoteResult<T> {
    data class Success<T>(val data: T) : RemoteResult<T>()
    data class Error<T>(val message: String, val code: Int? = null, val exception: Exception? = null) : RemoteResult<T>()
}