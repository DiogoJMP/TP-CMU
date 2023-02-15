package pt.ipp.estg.cmu.composables

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Grade
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import kotlinx.coroutines.launch
import pt.ipp.estg.cmu.classes.Charger
import pt.ipp.estg.cmu.classes.FavoriteCharger
import pt.ipp.estg.cmu.classes.VisitedCharger
import pt.ipp.estg.cmu.viewmodels.FavoritesVM
import pt.ipp.estg.cmu.viewmodels.HistoryVM

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DialogButton(
    charger: Charger,
    favoritesVM: FavoritesVM = viewModel(),
    historyVM: HistoryVM = viewModel(),
    type: String,
    context: Context
) {
    val gsonAddress = Gson().toJson(charger.addressInfo)
    val gsonStatus = Gson().toJson(charger.status)
    val gsonConnections = Gson().toJson(charger.connections)
    val gsonOperator = Gson().toJson(charger.operatorInfo)
    val gsonUsage = Gson().toJson(charger.usageType)

    Button(onClick = {
        if (type == "favorites") {
            favoritesVM.addFavorite(
                FavoriteCharger(
                    charger.id,
                    gsonAddress,
                    gsonStatus,
                    gsonConnections,
                    gsonUsage,
                    gsonOperator
                ),
                Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT)
            )
            Log.d("Chargerid", charger.id)
        } else {
            historyVM.visitCharger(
                VisitedCharger(
                    charger.id,
                    gsonAddress,
                    gsonStatus,
                    gsonConnections,
                    gsonUsage,
                    gsonOperator,
                    System.currentTimeMillis()
                ),
                Toast.makeText(context, "Marked as visited", Toast.LENGTH_SHORT)
            )

        }
    }
    ) {
        Icon(
            imageVector = if (type == "favorites") Icons.Outlined.Grade else Icons.Outlined.Check,
            contentDescription = type
        )
    }
}
