package pt.ipp.estg.cmu.navigation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import pt.ipp.estg.cmu.composables.screens.*
import pt.ipp.estg.cmu.viewmodels.LocationVM

@Composable
fun Navigation(navController: NavHostController, locationVM: LocationVM) {
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
                SitesScreen(locationVM)
            }
            composable(BottomScreen.FavoritesScreen.route) {
                FavoritesScreen()
            }
            composable(BottomScreen.HistoryScreen.route) {
                HistoryScreen()
            }
        }
    }
}
