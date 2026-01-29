package com.example.vinyl_vibe.model

import com.google.gson.annotations.SerializedName

data class Vinyl(
    val id: Int,

    // Aceptamos "titulo" o "nombre" por si el backend cambia
    @SerializedName("titulo", alternate = ["nombre", "name"])
    val titulo: String,

    val artista: String,
    val precio: Int,
    val genero: String,
    val descripcion: String,

    // AHORA LA IMAGEN ES UNA URL (Texto), NO UN ID
    @SerializedName("imagen", alternate = ["image", "img", "url"])
    val imagenUrl: String? = null,

    val esOferta: Boolean = false
)

