package pt.ipp.estg.cmu.composables.screens

import android.media.tv.TvContract.Channels.Logo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import pt.ipp.estg.cmu.classes.CustomFont
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
        LogoText()
        Spacer(modifier = Modifier.size(26.dp))
        Button(
            onClick = { navController.navigate(Screen.SignUpScreen.route) },
            colors = ButtonDefaults.buttonColors(Purple50)
        ) {
            Text(text = "Sign Up", fontFamily = CustomFont().titleFamily)
        }
        Button(
            onClick = { navController.navigate(Screen.SignInScreen.route) },
            colors = ButtonDefaults.buttonColors(Purple50)
        ) {
            Text(text = "Sign In", fontFamily = CustomFont().titleFamily)
        }
    }
}

@Composable
fun LogoText() {
    Text(
        text = "Powerless", color = Color.White, fontFamily = CustomFont().titleFamily,
        style = TextStyle(
            fontSize = 34.sp, shadow = Shadow(
                color = Color.Black,
                offset = Offset(5.0f, 10.0f),
                blurRadius = 3f
            )
        ),
    )
}