package com.example.vinyl_vibe.model

import com.google.gson.annotations.SerializedName

data class Vinyl(
    val id: Int,

    // --- CORRECCIÓN AQUÍ ---
    // Le decimos a Android: "Aunque yo lo llame 'titulo',
    // envíalo a internet con la etiqueta 'nombre'"
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
