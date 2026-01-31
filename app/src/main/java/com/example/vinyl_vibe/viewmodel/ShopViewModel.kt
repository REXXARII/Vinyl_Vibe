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

    // Funciones del Carrito
    fun addToCart(vinyl: Vinyl) { _cartItems.add(vinyl) }
    fun removeFromCart(vinyl: Vinyl) { _cartItems.remove(vinyl) }
    fun clearCart() { _cartItems.clear() }
    fun calculateTotal(): Int { return _cartItems.sumOf { it.precio } }

    // nuevas funciones de agregar, eliminar y editar productos

    // 1. AGREGAR PRODUCTO (Create)
    fun agregarProducto(vinyl: Vinyl) {
        viewModelScope.launch {
            try {
                Log.d("API_SHOP", "Intentando crear: ${vinyl.titulo}")
                // Llamamos a la API
                RetrofitClient.productService.crearProducto(vinyl)
                // Si funciona, recargamos la lista para ver el nuevo disco
                fetchProductos()
            } catch (e: Exception) {
                Log.e("API_SHOP", "Error al crear: ${e.message}")
            }
        }
    }

    // 2. ELIMINAR PRODUCTO (Delete)
    fun eliminarProducto(id: Int) {
        viewModelScope.launch {
            try {
                Log.d("API_SHOP", "Eliminando ID: $id")
                // Llamamos a la API
                RetrofitClient.productService.eliminarProducto(id)
                // Recargamos la lista
                fetchProductos()
            } catch (e: Exception) {
                Log.e("API_SHOP", "Error al eliminar: ${e.message}")
            }
        }
    }

    // 3. EDITAR PRODUCTO (Update)
    fun editarProducto(id: Int, vinyl: Vinyl) {
        viewModelScope.launch {
            try {
                Log.d("API_SHOP", "Editando ID: $id")
                // Llamamos a la API
                RetrofitClient.productService.editarProducto(id, vinyl)
                // Recargamos la lista
                fetchProductos()
            } catch (e: Exception) {
                Log.e("API_SHOP", "Error al editar: ${e.message}")
            }
        }
    }


// Buscar por ID (Corregido)
    fun buscarProductoPorId(id: Int, onResult: (Vinyl?) -> Unit) {
        viewModelScope.launch {
            try {
                // CORRECCIÓN: Usamos RetrofitClient.productService
                val resultado = RetrofitClient.productService.obtenerProductoPorId(id)
                onResult(resultado)
            } catch (e: Exception) {
                Log.e("API_SHOP", "Error al buscar ID: $id", e)
                onResult(null)
            }
        }
    }
}

