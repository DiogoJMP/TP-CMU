package pt.ipp.estg.cmu.composables.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import kotlinx.coroutines.launch
import pt.ipp.estg.cmu.api.openchargemap.*
import pt.ipp.estg.cmu.classes.Charger
import pt.ipp.estg.cmu.classes.FavoriteCharger
import pt.ipp.estg.cmu.composables.ChargerDetailsDialog
import pt.ipp.estg.cmu.viewmodels.FavoritesVM
import pt.ipp.estg.cmu.viewmodels.RatingsVM
import kotlin.collections.ArrayList

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FavoritesScreen(
    auth: FirebaseAuth,
    favoritesVM: FavoritesVM = viewModel(),
    ratingsVM: RatingsVM = viewModel()
) {
    val favorites by favoritesVM.getFavorites().collectAsState(emptyList())
    val dialogState = rememberSaveable { (mutableStateOf(false)) }
    val selectedCard = remember { (mutableStateOf(0)) }
    val coroutineScope = rememberCoroutineScope()
    val rating = remember { mutableStateOf("") }

    when {
        favorites.isEmpty() -> Text(
            modifier = Modifier.fillMaxWidth(),
            text = "You haven't marked any chargers as favorite yet"
        )
        else -> LazyColumn() {
            items(favorites.size) { index ->
                val charger = favoritedChargerToCharger(favorites[index])
                coroutineScope.launch {
                    rating.value = ratingsVM.getRating(charger.id)?.averageScore.toString()
                }
                Card(
                    border = BorderStroke(1.dp, Color.Blue),
                    modifier = Modifier
                        .padding(5.dp),
                ) {
                    Button(
                        onClick = {
                            dialogState.value = true
                            selectedCard.value = index
                        }) { Text("View more") }
                    when {
                        dialogState.value -> {
                            ChargerDetailsDialog(
                                charger = favoritedChargerToCharger(favorites[selectedCard.value]),
                                auth = auth,
                                dialogState = dialogState,
                                flag = "else"
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .padding(3.dp)
                            .fillMaxWidth()
                    ) {
                        charger.addressInfo.Title?.let { Text(it) }
                        charger.status.Title?.let { Text(it) }
                        Text(text = if (rating.value != "") rating.value else "No ratings")
                    }
                }
            }
        }
    }
}

fun favoritedChargerToCharger(favoritedCharger: FavoriteCharger): Charger {
    val addressInfo =
        Gson().fromJson(favoritedCharger.addressInfo, AddressInfo::class.java)
    val statusType =
        Gson().fromJson(favoritedCharger.statusType, StatusType::class.java)
    val connections = getObject<ArrayList<Connection>>(favoritedCharger.connections!!)
    val operatorInfo = Gson().fromJson(favoritedCharger.operatorInfo, OperatorInfo::class.java)
    val usageType = Gson().fromJson(favoritedCharger.usageType, UsageType::class.java)

    return Charger(
        favoritedCharger.id,
        addressInfo,
        statusType,
        connections,
        usageType,
        operatorInfo
    )

}