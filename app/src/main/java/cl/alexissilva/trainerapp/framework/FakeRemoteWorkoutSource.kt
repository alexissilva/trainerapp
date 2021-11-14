package cl.alexissilva.trainerapp.framework

import android.content.Context
import android.util.Log
import cl.alexissilva.trainerapp.data.RemoteResult
import cl.alexissilva.trainerapp.data.RemoteWorkoutSource
import cl.alexissilva.trainerapp.domain.Workout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeRemoteWorkoutSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson,
) : RemoteWorkoutSource {

    override suspend fun getWorkouts(): RemoteResult<List<Workout>> {
        return try {
            delay(500)
            val json = readJsonFile()
            val listType = object : TypeToken<List<Workout>>() {}.type
            val workoutList = gson.fromJson(json, listType) as List<Workout>
            RemoteResult.Success(workoutList)
        } catch (e: Exception) {
            Log.w("FakeRemoteWorkoutSource", "Exception $e")
            RemoteResult.Error("FakeRemoteError", null, e)
        }
    }

    private fun readJsonFile(): String {
        val inputStream = context.assets.open("dummy_workouts.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer, Charsets.UTF_8)
    }


}