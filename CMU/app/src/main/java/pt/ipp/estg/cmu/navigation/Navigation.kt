package pt.ipp.estg.cmu.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import pt.ipp.estg.cmu.composables.screens.*

@Composable
fun Navigation(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
    when (navBackStackEntry?.destination?.route) {
        Screen.SplashScreen.route -> {
            bottomBarState.value = false
        }
        Screen.SignUpScreen.route -> {
            bottomBarState.value = false
        }
        Screen.SignInScreen.route -> {
            bottomBarState.value = false
        }
        else -> {
            bottomBarState.value = true
        }
    }
    Scaffold(bottomBar = {
        BottomNavBar(
            navController = navController,
            bottomBarState.value
        )
    }) { paddingValues ->
        NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
            composable(route = Screen.SplashScreen.route) {
                SplashScreen(navController = navController, paddingValues)
            }
            composable(route = Screen.SignUpScreen.route) {
                SignUpScreen(navHostController = navController)
            }
            composable(route = Screen.SignInScreen.route) {
                SignInScreen(navHostController = navController)
            }
            composable(route = Screen.HomeScreen.route) {
                HomeScreen()
            }
            composable(BottomScreen.SitesScreen.route) {
                SitesScreen()
            }
            composable(BottomScreen.FavoritesScreen.route) {
                FavoritesScreen()
            }
        }
    }
}
