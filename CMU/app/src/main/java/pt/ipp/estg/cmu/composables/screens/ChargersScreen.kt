package pt.ipp.estg.cmu.composables.screens

import android.location.Location
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import pt.ipp.estg.cmu.classes.ChargerRating
import pt.ipp.estg.cmu.composables.ChargerDetailsDialog
import pt.ipp.estg.cmu.room.ChargerEntity
import pt.ipp.estg.cmu.viewmodels.ChargersVM
import pt.ipp.estg.cmu.viewmodels.RatingsVM

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChargersScreen(
    currentLocation: Location,
    chargersVM: ChargersVM = viewModel(),
    ratingsVM: RatingsVM = viewModel(),
    paddingValues: PaddingValues,
    auth: FirebaseAuth
) {
    chargersVM.fetchChargers(currentLocation)
    val chargers by chargersVM.chargerList.observeAsState(listOf())
    val dialogState = rememberSaveable { (mutableStateOf(false)) }
    val selectedCard = remember { (mutableStateOf(0)) }
    val coroutineScope = rememberCoroutineScope()
    val rating = remember { mutableStateOf("") }

    LazyColumn(Modifier.padding(paddingValues)) {
        items(chargers.size) { index ->
            val charger = chargerEntityToCharger(chargers[index])
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
                            charger = chargerEntityToCharger(chargers[selectedCard.value]),
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
                    Text(text = if (rating.value != "" || rating.value != null) rating.value else "No ratings")
                }
            }
        }
    }
}

fun chargerEntityToCharger(chargerEntity: ChargerEntity): Charger {
    val addressInfo =
        Gson().fromJson(chargerEntity.addressInfo, AddressInfo::class.java)
    val statusType =
        Gson().fromJson(chargerEntity.statusType, StatusType::class.java)
    val connections = getObject<ArrayList<Connection>>(chargerEntity.connections)
    val operatorInfo = Gson().fromJson(chargerEntity.operatorInfo, OperatorInfo::class.java)
    val usageType = Gson().fromJson(chargerEntity.usageType, UsageType::class.java)

    return Charger(
        chargerEntity.id.toString(),
        addressInfo,
        statusType,
        connections,
        usageType,
        operatorInfo
    )

}
