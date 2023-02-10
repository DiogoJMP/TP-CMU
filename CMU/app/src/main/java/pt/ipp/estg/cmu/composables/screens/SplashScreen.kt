package pt.ipp.estg.cmu.composables.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import pt.ipp.estg.cmu.navigation.Screen
import pt.ipp.estg.cmu.ui.theme.Purple40
import pt.ipp.estg.cmu.ui.theme.Purple50

@Composable
fun SplashScreen(navController: NavHostController, paddingValues: PaddingValues) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Purple40)
    ) {
        Button(
            onClick = { navController.navigate(Screen.SignUpScreen.route) },
            colors = ButtonDefaults.buttonColors(Purple50)
        ) {
            Text(text = "Sign Up")
        }
        Button(
            onClick = { navController.navigate(Screen.SignInScreen.route) },
            colors = ButtonDefaults.buttonColors(Purple50)
        ) {
            Text(text = "Sign In")
        }
    }
}