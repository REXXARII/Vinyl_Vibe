package com.example.vinyl_vibe.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinyl_vibe.data.RetrofitClient
import com.example.vinyl_vibe.model.Vinyl
import kotlinx.coroutines.launch

class ShopViewModel : ViewModel() {

    // Lista de productos (ahora viene de internet)
    private val _productos = mutableStateListOf<Vinyl>()
    val productos: List<Vinyl> get() = _productos

    // Carrito de compras
    private val _cartItems = mutableStateListOf<Vinyl>()
    val cartItems: List<Vinyl> get() = _cartItems

    init {
        // Apenas nace el ViewModel, descargamos los datos
        fetchProductos()
    }

    // FUNCION PARA DESCARGAR DISCOS DEL MS A (Puerto 8080)
    fun fetchProductos() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.productService.getProductos()
                if (response.isSuccessful && response.body() != null) {
                    _productos.clear()
                    _productos.addAll(response.body()!!)
                    Log.d("API_SHOP", "Productos cargados: ${_productos.size}")
                } else {
                    Log.e("API_SHOP", "Error al cargar productos: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("API_SHOP", "Error de conexión: ${e.message}")
            }
        }
    }

    // --- Funciones del Carrito (igual que antes) ---
    fun addToCart(vinyl: Vinyl) { _cartItems.add(vinyl) }
    fun removeFromCart(vinyl: Vinyl) { _cartItems.remove(vinyl) }
    fun clearCart() { _cartItems.clear() }
    fun calculateTotal(): Int { return _cartItems.sumOf { it.precio } }
}

