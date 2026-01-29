package com.example.vinyl_vibe.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vinyl_vibe.R
import com.example.vinyl_vibe.navigation.AppScreens
import com.example.vinyl_vibe.ui.theme.DarkBackground
import com.example.vinyl_vibe.ui.theme.NeonGreen
import com.example.vinyl_vibe.ui.theme.SurfaceGrey
import com.example.vinyl_vibe.ui.theme.TextWhite
import com.example.vinyl_vibe.viewmodel.ShopViewModel

import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale

//recibe vinylId por navegacion
// use launchedEffect con animatable para animacion del disco deslizandose ( offset)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    vinylId: Int?,
    viewModel: ShopViewModel = viewModel()
) {
    //cambiamos de local a internet
    // Buscamos en la lista que bajamos de internet (viewModel)
    val vinyl = viewModel.productos.find { it.id == vinylId }


    // Estado para la animacion
    val discOffset = remember { Animatable(0f) }

    // Efecto al abrir la pantalla
    LaunchedEffect(Unit) {
        discOffset.animateTo(
            targetValue = 130f,
            animationSpec = tween(durationMillis = 1000)
        )
    }

    // No disco = error
    if (vinyl == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Producto no encontrado", color = TextWhite)
        }
        return
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detalle", color = TextWhite) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = NeonGreen)
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(AppScreens.Cart.route) }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Ir al Carrito", tint = NeonGreen)
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //ZONA DE ANIMACION
            Box(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                // CAPA 1 (Fondo)
                Image(
                    painter = painterResource(id = R.drawable.vinyl_record),
                    contentDescription = null,
                    modifier = Modifier
                        .size(240.dp)
                        .offset(x = discOffset.value.dp)
                )

                // CAPA 2 (Frente)
                AsyncImage(
                    model = vinyl.imagenUrl, // Usamos la URL de internet
                    contentDescription = null,
                    modifier = Modifier
                        .size(280.dp) // Ajustamos el tamaño
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.vinyl_record), // Muestra el disco mientras carga
                    error = painterResource(R.drawable.vinyl_record) // Si falla la carga
                )


            }

            Spacer(modifier = Modifier.height(24.dp))

            // DATOS DEL PRODUCTO
            Text(text = vinyl.titulo, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = TextWhite)
            Text(text = vinyl.artista, fontSize = 20.sp, color = NeonGreen)

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceGrey),
                shape = RoundedCornerShape(50)
            ) {
                Text(
                    text = vinyl.genero,
                    color = TextWhite,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Sobre el album",
                fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextWhite,
                modifier = Modifier.align(Alignment.Start)
            )
            Text(
                text = vinyl.descripcion,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(32.dp))

            //BOTON DE COMPRA
            Button(
                onClick = {
                    viewModel.addToCart(vinyl)
                    navController.navigate(AppScreens.Cart.route)
                },
                colors = ButtonDefaults.buttonColors(containerColor = NeonGreen),
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Agregar por $${vinyl.precio}",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}
