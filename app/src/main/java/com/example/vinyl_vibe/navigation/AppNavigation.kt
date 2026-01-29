package com.example.vinyl_vibe.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vinyl_vibe.viewmodel.ShopViewModel
import com.example.vinyl_vibe.viewmodel.AuthViewModel
import com.example.vinyl_vibe.screens.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    //instancio los viewModel y los pasa a las pantallas. garantiza que el carrito no se vacie al cambiar de vista
    val shopViewModel: ShopViewModel = viewModel() // Carrito
    val authViewModel: AuthViewModel = viewModel() // Usuarios

    //la navegacion se gestiona con navHost centralizado en AppNavegation
    //maps para avanzar, popbackStack para retroceder
    //popUp para gestionar el flujo y evitar bucles
    NavHost(navController = navController, startDestination = AppScreens.Login.route) {

        // Pasamos authViewModel al Login
        composable(AppScreens.Login.route) {
            LoginScreen(navController, authViewModel)
        }

        // Pasamos authViewModel al Registro
        composable(AppScreens.Register.route) {
            RegisterScreen(navController, authViewModel)
        }

        composable(AppScreens.Home.route) { HomeScreen(navController) }

        composable(AppScreens.AboutUs.route) { AboutUsScreen(navController) }

        composable(
            route = AppScreens.Detail.route,
            arguments = listOf(navArgument("vinylId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("vinylId")
            DetailScreen(navController, id, viewModel = shopViewModel)
        }

        composable(AppScreens.Cart.route) {
            CartScreen(navController, viewModel = shopViewModel)
        }

        composable(AppScreens.Checkout.route) {
            CheckoutScreen(navController, viewModel = shopViewModel)
        }
    }
}
