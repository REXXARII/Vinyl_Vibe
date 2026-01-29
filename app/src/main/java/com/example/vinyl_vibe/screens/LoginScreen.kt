package com.example.vinyl_vibe.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vinyl_vibe.R
import com.example.vinyl_vibe.navigation.AppScreens
import com.example.vinyl_vibe.ui.theme.*
import com.example.vinyl_vibe.viewmodel.AuthViewModel

// el authVM se crea una vez en AppNavegation
//registro, escribir datos-> se guardan en AuthVM-> en login, este revisa la misma lista y encuentra el usuario creado
//resumen, ambas pantallas miran la misma lista

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel) { // Recibe AuthVM

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val infiniteTransition = rememberInfiniteTransition(label = "vinilo")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 360f,
        animationSpec = infiniteRepeatable(animation = tween(4000, easing = LinearEasing)),
        label = "rotacion"
    )//infiniteRepeatable para rotar la imagen

    Surface(modifier = Modifier.fillMaxSize(), color = DarkBackground) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // IMAGEN LOCAL
            Image(
                painter = painterResource(id = R.drawable.vinyl_record),
                contentDescription = "Logo",
                modifier = Modifier.size(180.dp).rotate(angle)
            )

            Spacer(modifier = Modifier.height(40.dp))
            Text("Vinyl Vibe", color = NeonGreen, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = email, onValueChange = { email = it; errorMessage = "" },
                label = { Text("Correo", color = Color.Gray) },
                singleLine = true,
                //diseño
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonGreen, unfocusedBorderColor = SurfaceGrey,
                    focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, cursorColor = NeonGreen
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password, onValueChange = { password = it; errorMessage = "" },
                label = { Text("Contraseña", color = Color.Gray) },
                singleLine = true, visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                //Diseño
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonGreen, unfocusedBorderColor = SurfaceGrey,
                    focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, cursorColor = NeonGreen
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (errorMessage.isNotEmpty()) {
                Text(errorMessage, color = Color.Red, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        errorMessage = "⚠️ Debes llenar todos los campos"
                    } else {
                        // Llamamos al login y esperamos la respuesta
                        authViewModel.login(email, password) { exito ->
                            if (exito) {
                                navController.navigate(AppScreens.Home.route) {
                                    popUpTo(AppScreens.Login.route) { inclusive = true }
                                }
                            } else {
                                errorMessage = "Usuario o contraseña incorrecta (o error de red)"
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = NeonGreen),
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Ingresar", color = Color.Black, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = { navController.navigate(AppScreens.Register.route) }) {
                Text("¿No tienes cuenta? Registrate aqui", color = NeonGreen)
            }
        }
    }
}
