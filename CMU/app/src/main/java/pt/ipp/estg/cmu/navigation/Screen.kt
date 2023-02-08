package pt.ipp.estg.cmu.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash")
    object HomeScreen : Screen("home")
    object SignInScreen : Screen("signIn")
    object SignUpScreen : Screen("signUp")
}

sealed class BottomScreen(val route: String, val title: String, val icon: ImageVector) {
    object FavoritesScreen :
        BottomScreen("favorites", "Favorites", Icons.Default.Star)

    object SitesScreen :
        BottomScreen("sites", "Sites", Icons.Default.List)
}