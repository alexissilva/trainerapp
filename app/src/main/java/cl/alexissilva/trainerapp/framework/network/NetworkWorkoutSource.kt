package cl.alexissilva.trainerapp.framework.network

import android.util.Log
import cl.alexissilva.trainerapp.core.data.RemoteResult
import cl.alexissilva.trainerapp.core.data.RemoteWorkoutSource
import cl.alexissilva.trainerapp.core.domain.Workout
import javax.inject.Inject

class NetworkWorkoutSource @Inject constructor(
    private val workoutApi: WorkoutApi
) : RemoteWorkoutSource {
    override suspend fun getWorkouts(): RemoteResult<List<Workout>> {
        return try {
            val response = workoutApi.getWorkouts()
            if (response.isSuccessful) {
                response.body()?.let {
                    RemoteResult.Success(it.workouts)
                } ?: RemoteResult.Error("Null body: ${response.message()}", response.code())
            } else {
                RemoteResult.Error(response.message(), response.code())
            }
        } catch (e: Exception) {
            val message = "Exception: ${e.message}"
            Log.d("NetworkWorkoutSource", message)
            RemoteResult.Error(message, null, e)
        }
    }
}