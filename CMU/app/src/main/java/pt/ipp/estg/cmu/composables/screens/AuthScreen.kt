package pt.ipp.estg.cmu.composables.screens

import android.app.PendingIntent
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import pt.ipp.estg.cmu.R
import pt.ipp.estg.cmu.classes.CustomFont
import pt.ipp.estg.cmu.navigation.Screen
import pt.ipp.estg.cmu.ui.theme.Purple40
import pt.ipp.estg.cmu.ui.theme.Purple50

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(navController: NavHostController, type: String, auth: FirebaseAuth) {
    var email by remember { mutableStateOf(("")) }
    var passwd by remember { mutableStateOf(("")) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Purple40)
    ) {
        LogoText()
        Spacer(Modifier.size(4.dp))
        TextField(
            leadingIcon = { LeadingIconView(Icons.Outlined.Mail) },
            value = email,
            onValueChange = {
                email = it
            },
            shape = RoundedCornerShape(50),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 3.dp)
                .fillMaxWidth()
                .size(56.dp),
            singleLine = true,
        )
        TextField(
            leadingIcon = { LeadingIconView(Icons.Outlined.Password) },
            value = passwd,
            onValueChange = {
                passwd = it
            },
            shape = RoundedCornerShape(50),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 3.dp)
                .fillMaxWidth()
                .size(56.dp),
            singleLine = true,
            //6 char min
        )
        AuthButton(
            navController = navController,
            email = email,
            passwd = passwd,
            type = type,
            auth = auth
        )
    }
}

@Composable
fun AuthButton(
    navController: NavHostController,
    email: String,
    passwd: String,
    type: String,
    auth: FirebaseAuth
) {
    val context = LocalContext.current
    val builder = NotificationCompat.Builder(context, "POWERLESS_NOTIFICATION")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("Welcome!")
        .setContentText("Tap this to dismiss")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
        .setContentIntent(PendingIntent.getActivity(context, 0, Intent(), 0))
    
    Button(
        onClick = {
            if (type == "Sign Up") {
                auth.createUserWithEmailAndPassword(email, passwd)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navController.navigate(Screen.HomeScreen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                            with(NotificationManagerCompat.from(context)) {
                                notify(6969, builder.build())
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Password must be at least 6 characters long",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
            if (type == "Sign In") {
                auth.signInWithEmailAndPassword(email, passwd)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navController.navigate(Screen.HomeScreen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                            with(NotificationManagerCompat.from(context)) {
                                notify(6969, builder.build())
                            }
                        }
                    }
            }
        },
        colors = ButtonDefaults.buttonColors(Purple50)
    ) {
        Text(text = type, fontFamily = CustomFont().titleFamily)
    }
}

@Composable
fun LeadingIconView(icon: ImageVector) {
    Icon(
        icon,
        contentDescription = "",
        tint = Purple50
    )
}

