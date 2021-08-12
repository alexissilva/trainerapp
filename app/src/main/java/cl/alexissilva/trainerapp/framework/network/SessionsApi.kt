package cl.alexissilva.trainerapp.framework.network

import retrofit2.Response
import retrofit2.http.GET

interface SessionsApi {

    @GET("/sessions")
    suspend fun getSessions(): Response<SessionsResponse>
}