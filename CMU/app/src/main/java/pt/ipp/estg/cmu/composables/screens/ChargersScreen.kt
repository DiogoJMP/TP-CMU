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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import pt.ipp.estg.cmu.api.openchargemap.*
import pt.ipp.estg.cmu.classes.Charger
import pt.ipp.estg.cmu.composables.ChargerDetailsDialog
import pt.ipp.estg.cmu.room.ChargerEntity
import pt.ipp.estg.cmu.ui.theme.Purple40
import pt.ipp.estg.cmu.viewmodels.ChargersVM

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChargersScreen(
    currentLocation: Location,
    chargersVM: ChargersVM = viewModel(),
    paddingValues: PaddingValues,
    auth: FirebaseAuth
) {
    chargersVM.fetchChargers(currentLocation)
    val chargers by chargersVM.chargerList.observeAsState(listOf())
    var chargerList: ArrayList<Charger> = arrayListOf()
    for (charger in chargers) {
        chargerList.add(chargerEntityToCharger(charger))
    }

    val sortedChargers = chargerList.sortedWith(compareBy { it.addressInfo.Distance })
    val dialogState = rememberSaveable { (mutableStateOf(false)) }
    val selectedCard = remember { (mutableStateOf(0)) }

    LazyColumn(Modifier.padding(paddingValues)) {
        items(sortedChargers.size) { index ->
            Card(
                border = BorderStroke(1.dp, Purple40),
                modifier = Modifier
                    .padding(5.dp),
            ) {
                when {
                    dialogState.value -> {
                        ChargerDetailsDialog(
                            charger = sortedChargers[selectedCard.value],
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
                        sortedChargers[index].addressInfo.Title?.let { Text(it) }
                        sortedChargers[index].status.Title?.let { Text(it) }
                        Text(String.format("%.2f km", sortedChargers[index].addressInfo.Distance))
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
