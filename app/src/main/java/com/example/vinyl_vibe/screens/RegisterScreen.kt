package com.example.vinyl_vibe.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vinyl_vibe.R
import com.example.vinyl_vibe.navigation.AppScreens
import com.example.vinyl_vibe.ui.theme.DarkBackground
import com.example.vinyl_vibe.ui.theme.NeonGreen
import com.example.vinyl_vibe.ui.theme.SurfaceGrey
import com.example.vinyl_vibe.ui.theme.TextWhite
import com.example.vinyl_vibe.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {

    // VARIABLES DE TEXTO 
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // VARIABLE DE ERROR 
    var errorMessage by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = DarkBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Botón Volver
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                //para el boton de volver atras (ArrowBack) usamos "popBackStack" que elimina
                //la vista actual de la pila actual y muestra la anterior automaticamente
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = NeonGreen)
                }
            }

            // Logo pequeño
            Image(
                painter = painterResource(id = R.drawable.vinyl_record),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("Crear Cuenta", color = NeonGreen, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Text("unete al club del vinilo", color = Color.Gray, fontSize = 16.sp)

            Spacer(modifier = Modifier.height(30.dp))

            // FORMULARIO 

            OutlinedTextField(
                value = name,
                onValueChange = { name = it; errorMessage = "" },
                label = { Text("Nombre Completo", color = Color.Gray) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonGreen, unfocusedBorderColor = SurfaceGrey,
                    focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, cursorColor = NeonGreen
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it; errorMessage = "" },
                label = { Text("Correo Electronico", color = Color.Gray) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonGreen, unfocusedBorderColor = SurfaceGrey,
                    focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, cursorColor = NeonGreen
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it; errorMessage = "" },
                label = { Text("Contraseña", color = Color.Gray) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonGreen, unfocusedBorderColor = SurfaceGrey,
                    focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, cursorColor = NeonGreen
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it; errorMessage = "" },
                label = { Text("Confirmar Contraseña", color = Color.Gray) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonGreen, unfocusedBorderColor = SurfaceGrey,
                    focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, cursorColor = NeonGreen
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // MENSAJE DE ERROR ROJO
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // BOTON CON VALIDACIONES (visuales en tiempo real)
            Button(
                onClick = {
                    // 1. REGLA: Nada vacio
                    if (name.isBlank() || email.isBlank() || password.isBlank()) {
                        errorMessage = "Todos los campos son obligatorios"
                    }
                    // 2. REGLA: Nombre decente
                    else if (name.length < 3) {
                        errorMessage = "El nombre es muy corto"
                    }
                    // 3. REGLA: Correo real (@)
                    else if (!email.contains("@")) {
                        errorMessage = "Ingresa un correo valido (@)"
                    }
                    // 4. REGLA: Contraseña segura (minimo 6)
                    else if (password.length < 6) {
                        errorMessage = "La contraseña debe tener al menos 6 caracteres"
                    }
                    // 5. REGLA: Coincidencia
                    else if (password != confirmPassword) {
                        errorMessage = "Las contraseñas no coinciden"
                    }
                    // SI PASA, GUARDAMOS
                    else {
                        authViewModel.register(name, email, password) { exito ->
                            if (exito) {
                                navController.navigate(AppScreens.Login.route)
                            } else {
                                errorMessage = "Error al registrar. Revisa tu conexio " +
                                        "`ñ+n."
                            }
                        }
                    }

                },
                colors = ButtonDefaults.buttonColors(containerColor = NeonGreen),
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Crear Cuenta", color = Color.Black, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { navController.popBackStack() }) {
                Text(text = "Volver al Login", color = NeonGreen)
            }
        }
    }
}
