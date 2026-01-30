package com.example.vinyl_vibe.model

import com.google.gson.annotations.SerializedName

data class Vinyl(
    val id: Int,

    // --- CORRECCIÓN AQUÍ ---
    @SerializedName("nombre") 
    val titulo: String,

    val artista: String,
    val precio: Int,
    val genero: String,
    val descripcion: String,

    @SerializedName("imagen")
    val imagenUrl: String? = null,

    val esOferta: Boolean = false
)
