package cl.alexissilva.trainerapp.framework

import android.content.Context
import cl.alexissilva.trainerapp.data.RemoteResult
import cl.alexissilva.trainerapp.data.RemoteSessionSource
import cl.alexissilva.trainerapp.domain.Session
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeRemoteSessionSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson,
) : RemoteSessionSource {

    override suspend fun getSessions(): RemoteResult<List<Session>> {
        return try {
            delay(500)
            val json = readJsonFile()
            val listType = object : TypeToken<List<Session>>() {}.type
            val sessionList = gson.fromJson(json, listType) as List<Session>
            RemoteResult.Success(sessionList)
        } catch (e: Exception) {
            RemoteResult.Error("FakeRemoteError", null, e)
        }
    }

    private fun readJsonFile(): String {
        val inputStream = context.assets.open("dummy_sessions.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer, Charsets.UTF_8)
    }


}