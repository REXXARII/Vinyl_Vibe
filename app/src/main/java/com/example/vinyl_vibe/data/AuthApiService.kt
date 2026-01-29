package com.example.vinyl_vibe.data

import com.example.vinyl_vibe.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("api/usuarios/login")
    suspend fun login(@Body user: User): Response<User>

    @POST("api/usuarios/registro")
    suspend fun register(@Body user: User): Response<User>
}

