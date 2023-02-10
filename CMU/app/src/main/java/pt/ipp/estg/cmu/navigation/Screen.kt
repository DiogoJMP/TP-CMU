package pt.ipp.estg.cmu.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Grade
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Power
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash")
    object HomeScreen : Screen("home")
    object SignInScreen : Screen("auth/signIn")
    object SignUpScreen : Screen("auth/signUp")
}

sealed class BottomScreen(val route: String, val title: String, val icon: ImageVector) {
    object FavoritesScreen :
        BottomScreen("favorites", "Favorites", Icons.Outlined.Grade)

    object SitesScreen :
        BottomScreen("sites", "Sites", Icons.Outlined.Power)

    object HistoryScreen :
        BottomScreen("history", "History", Icons.Outlined.History)
}