package cl.alexissilva.trainerapp.framework.network

import retrofit2.Response
import retrofit2.http.GET

interface WorkoutApi {

    @GET("/workouts")
    suspend fun getWorkouts(): Response<WorkoutsResponse>
}