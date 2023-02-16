package pt.ipp.estg.cmu.composables.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import pt.ipp.estg.cmu.api.openchargemap.*
import pt.ipp.estg.cmu.classes.Charger
import pt.ipp.estg.cmu.classes.CustomFont
import pt.ipp.estg.cmu.classes.FavoriteCharger
import pt.ipp.estg.cmu.composables.ChargerDetailsDialog
import pt.ipp.estg.cmu.ui.theme.Purple40
import pt.ipp.estg.cmu.viewmodels.FavoritesVM
import kotlin.collections.ArrayList

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FavoritesScreen(
    auth: FirebaseAuth,
    favoritesVM: FavoritesVM = viewModel()
) {
    val favorites by favoritesVM.getFavorites().collectAsState(emptyList())
    val dialogState = rememberSaveable { (mutableStateOf(false)) }
    val selectedCard = remember { (mutableStateOf(0)) }

    when {
        favorites.isEmpty() ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Purple40)
            ) {
                Text(
                    fontSize = 14.sp,
                    text = "You haven't marked any chargers as favorite yet",
                    fontFamily = CustomFont().titleFamily,
                    color = Color.White
                )
            }

        else -> LazyColumn(Modifier.background(Purple40)) {
            items(favorites.size) { index ->
                val charger = favoriteChargerToCharger(favorites[index])
                Card(
                    border = BorderStroke(1.dp, Purple40),
                    modifier = Modifier
                        .padding(5.dp),
                ) {
                    when {
                        dialogState.value -> {
                            ChargerDetailsDialog(
                                charger = favoriteChargerToCharger(favorites[selectedCard.value]),
                                auth = auth,
                                dialogState = dialogState,
                                flag = "else"
                            )
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier
                                .padding(10.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            charger.addressInfo.Title?.let { Text(it) }
                            charger.status.Title?.let { Text(it) }
                        }
                        Column(
                            modifier = Modifier
                                .padding(3.dp)
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .align(Alignment.CenterVertically),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.End
                        ) {

                            Button(
                                onClick = {
                                    dialogState.value = true
                                    selectedCard.value = index
                                }, modifier = Modifier.align(Alignment.End)
                            ) { Text("View more") }
                        }
                    }
                }
            }
        }
    }
}

fun favoriteChargerToCharger(favoriteCharger: FavoriteCharger): Charger {
    val addressInfo =
        Gson().fromJson(favoriteCharger.addressInfo, AddressInfo::class.java)
    val statusType =
        Gson().fromJson(favoriteCharger.statusType, StatusType::class.java)
    val connections = getObject<ArrayList<Connection>>(favoriteCharger.connections!!)
    val operatorInfo = Gson().fromJson(favoriteCharger.operatorInfo, OperatorInfo::class.java)
    val usageType = Gson().fromJson(favoriteCharger.usageType, UsageType::class.java)

    return Charger(
        favoriteCharger.id,
        addressInfo,
        statusType,
        connections,
        usageType,
        operatorInfo
    )

}