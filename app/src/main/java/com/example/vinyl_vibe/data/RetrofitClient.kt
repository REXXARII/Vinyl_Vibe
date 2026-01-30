package com.example.vinyl_vibe.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // 1. LAS DIRECCIONES (IP del Duoc)
    private const val URL_USUARIOS = "http://10.155.137.248:8081/"
    private const val URL_PRODUCTOS = "http://10.155.137.248:8080/"

    // --- CONEXION B: USUARIOS (Puerto 8081) ---
    private val retrofitUsuarios: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(URL_USUARIOS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val authService: AuthApiService by lazy {
        retrofitUsuarios.create(AuthApiService::class.java)
    }

    // --- CONEXION A: PRODUCTOS (Puerto 8080) ---
    private val retrofitProductos: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(URL_PRODUCTOS) // Ojo: usa la URL de productos
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Esta es la variable que faltaba
    val productService: ProductApiService by lazy {
        retrofitProductos.create(ProductApiService::class.java)
    }
}

