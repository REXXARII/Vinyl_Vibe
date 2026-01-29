package com.example.vinyl_vibe.navigation

sealed class AppScreens(val route: String) {
    object Login : AppScreens("login_screen")
    object Register : AppScreens("register_screen")
    object Home : AppScreens("home_screen")
    object Detail : AppScreens("detail_screen/{vinylId}")
    object Cart : AppScreens("cart_screen")
    object Checkout : AppScreens("checkout_screen")
    object AboutUs : AppScreens("about_us_screen")
}
//define rutas de forma segura
//evitando erroes de tipeo al navegar