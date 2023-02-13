package pt.ipp.estg.cmu.composables.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import pt.ipp.estg.cmu.classes.CustomFont
import pt.ipp.estg.cmu.navigation.BottomScreen
import pt.ipp.estg.cmu.ui.theme.Purple40
import pt.ipp.estg.cmu.ui.theme.Purple50

@Composable
fun HomeScreen() {
    Text(text = "Home")
}

@Composable
fun BottomNavBar(
    navController: NavHostController,
    bottomBarState: Boolean
) {
    AnimatedVisibility(
        visible = bottomBarState,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        BottomNavigation(backgroundColor = Purple40) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            items.forEach { screen ->
                BottomNavigationItem(
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    icon = {
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = screen.title,
                        )
                    },
                    label = {
                        Text(
                            screen.title,
                            fontFamily = CustomFont().titleFamily,
                            fontSize = 14.sp
                        )
                    },
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Purple50
                )
            }
        }
    }
}

val items = listOf(
    BottomScreen.ChargersScreen,
    BottomScreen.FavoritesScreen,
    BottomScreen.HistoryScreen
)