package com.example.vinyl_vibe.data

import com.example.vinyl_vibe.model.Vinyl
import retrofit2.Response
import retrofit2.http.GET

interface ProductApiService {

    // Aqui definimos la "llamada" para traer los productos
    // El servidor responde en la ruta "/productos"
    @GET("productos")
    suspend fun getProductos(): Response<List<Vinyl>>
}

