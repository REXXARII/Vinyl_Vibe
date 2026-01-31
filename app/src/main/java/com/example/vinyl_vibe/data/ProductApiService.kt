package com.example.vinyl_vibe.data

import com.example.vinyl_vibe.model.Vinyl
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductApiService {

    // Aqui definimos la "llamada" para traer los productos
    // El servidor responde en la ruta "/productos"
    @GET("api/productos")
    suspend fun getProductos(): Response<List<Vinyl>>

    @GET("api/productos/{id}")
    suspend fun obtenerProductoPorId(@Path("id") id: Int): Vinyl

    // crear, eliminar, editar

    @POST("api/productos")
    suspend fun crearProducto(@Body vinyl: Vinyl): Vinyl

    @DELETE("api/productos/{id}")
    suspend fun eliminarProducto(@Path("id") id: Int)

    @PUT("api/productos/{id}")
    suspend fun editarProducto(@Path("id") id: Int, @Body vinyl: Vinyl): Vinyl

}

