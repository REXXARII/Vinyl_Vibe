package com.example.vinyl_vibe.model

import com.google.gson.annotations.SerializedName

data class User(
    // Mapeamos lo que tu App envia -> A lo que el Backend espera
    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("username") // Backend espera "username"
    val correo: String,

    @SerializedName("password") // Backend espera "password"
    val contrasena: String,

    // Datos extra que manda el backend
    val id: Long? = null,
    val rol: String = "USER"
)
