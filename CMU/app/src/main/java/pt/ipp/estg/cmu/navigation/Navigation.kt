package pt.ipp.estg.cmu.navigation

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import pt.ipp.estg.cmu.composables.screens.*
import pt.ipp.estg.cmu.viewmodels.ChargersVM
import pt.ipp.estg.cmu.viewmodels.LocationVM

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(
    navController: NavHostController,
    locationVM: LocationVM,
    auth: FirebaseAuth = Firebase.auth
) {
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
                SplashScreen(navController = navController)
            }
            composable(route = Screen.SignUpScreen.route) {
                AuthScreen(navController = navController, type = "Sign Up", auth)
            }
            composable(route = Screen.SignInScreen.route) {
                AuthScreen(navController = navController, type = "Sign In", auth)
            }
            composable(route = Screen.HomeScreen.route) {
                HomeScreen()
            }
            composable(BottomScreen.ChargersScreen.route) {
                val currentLocation by locationVM.location.observeAsState()
                ChargersScreen(
                    currentLocation = currentLocation!!,
                    paddingValues = paddingValues,
                    auth = auth
                )
            }
            composable(BottomScreen.FavoritesScreen.route) {
                FavoritesScreen(auth = auth)
            }
            composable(BottomScreen.HistoryScreen.route) {
                HistoryScreen(auth = auth)
            }
        }
    }
}
