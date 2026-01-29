package com.example.vinyl_vibe.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vinyl_vibe.navigation.AppScreens
import com.example.vinyl_vibe.ui.theme.DarkBackground
import com.example.vinyl_vibe.ui.theme.NeonGreen
import com.example.vinyl_vibe.ui.theme.SurfaceGrey
import com.example.vinyl_vibe.ui.theme.TextWhite
import com.example.vinyl_vibe.viewmodel.ShopViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    navController: NavController,
    viewModel: ShopViewModel = viewModel()
) {
    // ESTADOS DEL FORMULARIO
    var nombre by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

    // ESTADO DE ERROR Y EXITO
    var errorMessage by remember { mutableStateOf("") }
    var compraExitosa by remember { mutableStateOf(false) }

    // compra exitosa -> "Gracias"
    if (compraExitosa) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBackground)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = NeonGreen,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text("¡Pedido Recibido!", color = NeonGreen, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Gracias $nombre. Te contactaremos al $telefono para coordinar el pago contra entrega.",
                color = TextWhite,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = {
                    // Volver al inicio y borrar historial para no volver aqui
                    navController.navigate(AppScreens.Home.route) {
                        //al terminar la compra se borra el historial del carrito y devuelve al
                        //usuario al home y q no pueda volver al formulario de pago
                        popUpTo(AppScreens.Home.route) { inclusive = true }
                    }
                    //para limpiar la pila de navegacion y el usuario no regrese a paginas q no deba ver
                },
                colors = ButtonDefaults.buttonColors(containerColor = NeonGreen),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Volver a la Tienda", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
        return
    }

    // PANTALLA DE FORMULARIO (Si no ha comprado aun)
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Finalizar Compra", color = TextWhite) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = NeonGreen)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = DarkBackground)
            )
        },
        containerColor = DarkBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {

            Text("Datos de Envio", color = NeonGreen, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))

            // CAMPO NOMBRE
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre quien recibe") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonGreen,
                    unfocusedBorderColor = SurfaceGrey,
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    cursorColor = NeonGreen
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // CAMPO DIRECCIoN
            OutlinedTextField(
                value = direccion,
                onValueChange = { direccion = it },
                label = { Text("Direccion (Calle, Número, Comuna)") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonGreen,
                    unfocusedBorderColor = SurfaceGrey,
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    cursorColor = NeonGreen
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // CAMPO TELEFONO (Solo Numeros)
            OutlinedTextField(
                value = telefono,
                onValueChange = {
                    // Filtro para aceptar solo numeros mientras escribes
                    if (it.all { char -> char.isDigit() }) {
                        telefono = it
                    }
                },
                label = { Text("Telefono (+569...)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonGreen,
                    unfocusedBorderColor = SurfaceGrey,
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    cursorColor = NeonGreen
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // MENSAJE DE ERROR
            if (errorMessage.isNotEmpty()) {
                Text(errorMessage, color = Color.Red, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(16.dp))
            }

            // BOTON CONFIRMAR
            Button(
                onClick = {
                    // --- VALIDACIONES ---
                    if (nombre.isBlank() || direccion.isBlank() || telefono.isBlank()) {
                        errorMessage = "Por favor completa todos los campos."
                    } else if (nombre.length < 3) {
                        errorMessage = "El nombre es muy corto."
                    } else if (direccion.length < 10) {
                        errorMessage = "La direccion debe ser más detallada."
                    } else if (telefono.length < 8) {
                        errorMessage = "El telefono debe tener al menos 8 digitos."
                    } else {
                        // SI PASA TODO:
                        viewModel.clearCart() // Vaciamos el carro
                        compraExitosa = true  // Cambiamos el estado para mostrar el mensaje de exito
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = NeonGreen),
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Confirmar Pedido (Pago Contra Entrega)", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}
