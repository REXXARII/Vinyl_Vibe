package com.example.vinyl_vibe.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.vinyl_vibe.R
import com.example.vinyl_vibe.model.Vinyl
import com.example.vinyl_vibe.navigation.AppScreens
import com.example.vinyl_vibe.ui.theme.DarkBackground
import com.example.vinyl_vibe.ui.theme.NeonGreen
import com.example.vinyl_vibe.ui.theme.SurfaceGrey
import com.example.vinyl_vibe.ui.theme.TextWhite
import com.example.vinyl_vibe.viewmodel.ShopViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: ShopViewModel = viewModel()) {

    // AQUI ESTA EL CAMBIO: Leemos la lista del ViewModel (Internet), no del DataSource
    val allVinyls = viewModel.productos
    //cambiamos it.ofertas x el actual
    val ofertas = allVinyls.filter { it.precio < 35000 }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(painter = painterResource(id = R.drawable.vinyl_record), contentDescription = null, modifier = Modifier.size(32.dp), tint = Color.Unspecified)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Vinyl Vibe Online", color = NeonGreen, fontWeight = FontWeight.Bold)
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(AppScreens.Cart.route) }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito", tint = TextWhite)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = DarkBackground)
            )
        },
        containerColor = DarkBackground
    ) { paddingValues ->

        // Si aun no cargan los productos, mostramos un aviso
        if (allVinyls.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = NeonGreen)
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(paddingValues)
            ) {
                // ... (El resto de la estructura es igual, solo cambian las Cards abajo)

                item(span = { GridItemSpan(2) }) {
                    Text("Ofertas del Mes 🔥", color = TextWhite, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                }

                item(span = { GridItemSpan(2) }) {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(ofertas) { vinyl ->
                            OfferCard(vinyl) {
                                navController.navigate(AppScreens.Detail.route.replace("{vinylId}", vinyl.id.toString()))
                            }
                        }
                    }
                }

                item(span = { GridItemSpan(2) }) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Catálogo Completo 💿", color = TextWhite, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                }

                items(allVinyls) { vinyl ->
                    ProductGridItem(vinyl) {
                        navController.navigate(AppScreens.Detail.route.replace("{vinylId}", vinyl.id.toString()))
                    }
                }

                // Boton About Us y footer...
                item(span = { GridItemSpan(2) }) {
                    Button(
                        onClick = { navController.navigate(AppScreens.AboutUs.route) },
                        colors = ButtonDefaults.buttonColors(containerColor = SurfaceGrey),
                        modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
                    ) {
                        Text("¿Quiénes Somos?", color = TextWhite)
                    }
                }
            }
        }
    }
}

@Composable
fun OfferCard(vinyl: Vinyl, onClick: () -> Unit) {
    Card(
        modifier = Modifier.width(260.dp).height(140.dp).clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = SurfaceGrey),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // USAMOS ASYNC IMAGE PARA URLS
            AsyncImage(
                model = vinyl.imagenUrl, // URL de internet
                contentDescription = null,
                modifier = Modifier.width(140.dp).fillMaxHeight(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.vinyl_record), // Mientras carga
                error = painterResource(R.drawable.vinyl_record) // Si falla
            )
            Column(modifier = Modifier.padding(12.dp).fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                Text("OFERTA", color = NeonGreen, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Text(vinyl.titulo, color = TextWhite, fontWeight = FontWeight.Bold, maxLines = 2)
                Text("$${vinyl.precio}", color = Color.Gray)
            }
        }
    }
}

@Composable
fun ProductGridItem(vinyl: Vinyl, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = SurfaceGrey),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            // USAMOS ASYNC IMAGE PARA URLS
            AsyncImage(
                model = vinyl.imagenUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(160.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.vinyl_record),
                error = painterResource(R.drawable.vinyl_record)
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(vinyl.titulo, color = TextWhite, fontWeight = FontWeight.Bold, maxLines = 1)
                Text(vinyl.artista, color = Color.Gray, fontSize = 12.sp, maxLines = 1)
                Text("$${vinyl.precio}", color = NeonGreen, fontWeight = FontWeight.Bold)
            }
        }
    }
}

