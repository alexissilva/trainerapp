package cl.alexissilva.trainerapp.framework.network

import android.util.Log
import cl.alexissilva.trainerapp.data.RemoteResult
import cl.alexissilva.trainerapp.data.RemoteSessionSource
import cl.alexissilva.trainerapp.domain.Session
import javax.inject.Inject

class NetworkSessionSource @Inject constructor(
    private val sessionApi: SessionsApi
) : RemoteSessionSource {
    override suspend fun getSessions(): RemoteResult<List<Session>> {
        return try {
            val response = sessionApi.getSessions()
            if (response.isSuccessful) {
                response.body()?.let {
                    RemoteResult.Success(it.sessions)
                } ?: RemoteResult.Error("Null body: ${response.message()}", response.code())
            } else {
                RemoteResult.Error(response.message(), response.code())
            }
        } catch (e: Exception) {
            val message = "Exception: ${e.message}"
            Log.d("NetworkSessionSource", message)
            RemoteResult.Error(message, null, e)
        }
    }
}