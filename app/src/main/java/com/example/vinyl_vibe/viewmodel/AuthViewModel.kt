package com.example.vinyl_vibe.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinyl_vibe.data.RetrofitClient
import com.example.vinyl_vibe.model.User
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    // LOGIN REAL
    fun login(correo: String, pass: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                // Usuario temporal para enviar
                val loginUser = User(nombre = "", correo = correo, contrasena = pass)
                val response = RetrofitClient.authService.login(loginUser)

                if (response.isSuccessful && response.body() != null) {
                    onResult(true) // Entro
                } else {
                    onResult(false) // Contraseña mal o usuario no existe
                }
            } catch (e: Exception) {
                Log.e("LOGIN", "Error de conexion: ${e.message}")
                onResult(false) // Error de red (IP mal o server apagado)
            }
        }
    }

    // REGISTRO REAL
    fun register(nombre: String, correo: String, pass: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val newUser = User(nombre = nombre, correo = correo, contrasena = pass)
                val response = RetrofitClient.authService.register(newUser)

                if (response.isSuccessful) {
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }
}

