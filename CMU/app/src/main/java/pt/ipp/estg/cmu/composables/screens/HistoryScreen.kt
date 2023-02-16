package pt.ipp.estg.cmu.composables.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.google.gson.reflect.TypeToken
import pt.ipp.estg.cmu.api.openchargemap.*
import pt.ipp.estg.cmu.classes.Charger
import pt.ipp.estg.cmu.classes.CustomFont
import pt.ipp.estg.cmu.classes.VisitedCharger
import pt.ipp.estg.cmu.composables.ChargerDetailsDialog
import pt.ipp.estg.cmu.ui.theme.Purple40
import pt.ipp.estg.cmu.ui.theme.Purple50
import pt.ipp.estg.cmu.viewmodels.HistoryVM
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryScreen(
    auth: FirebaseAuth,
    historyVM: HistoryVM = viewModel()
) {
    val history by historyVM.getVisited().collectAsState(emptyList())

    val sortedChargers = history.sortedWith(compareBy { it.timeVisited })

    val dialogState = rememberSaveable { (mutableStateOf(false)) }
    val selectedCard = remember { (mutableStateOf(0)) }

    when {
        history.isEmpty() ->
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
                    text = "You haven't visited any chargers yet",
                    fontFamily = CustomFont().titleFamily,
                    color = Color.White
                )
            }
        else ->
            Column(
                Modifier
                    .background(Purple40)
                    .fillMaxHeight()
            ) {
                LazyColumn() {
                    items(sortedChargers.size) { index ->
                        val charger = visitedChargerToCharger(sortedChargers[index])
                        Card(
                            border = BorderStroke(1.dp, Purple40),
                            modifier = Modifier
                                .padding(5.dp),
                        ) {
                            when {
                                dialogState.value -> {
                                    ChargerDetailsDialog(
                                        charger = visitedChargerToCharger(sortedChargers[selectedCard.value]),
                                        auth = auth,
                                        dialogState = dialogState,
                                        flag = "history"
                                    )
                                }
                            }
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Column(
                                    modifier = Modifier
                                        .padding(10.dp),
                                    verticalArrangement = Arrangement.Top,
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    charger.addressInfo.Title?.let { Text(it) }
                                    Text(SimpleDateFormat("dd/MM/yy HH:mm").format(sortedChargers[index].timeVisited))
                                }
                                Column(
                                    modifier = Modifier
                                        .padding(3.dp)
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                        .align(Alignment.CenterVertically),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Button(
                                        onClick = {
                                            dialogState.value = true
                                            selectedCard.value = index
                                        },
                                        modifier = Modifier.align(Alignment.End)
                                    ) { Text("View more") }
                                }
                            }
                        }
                    }
                }
            }
    }
}

fun visitedChargerToCharger(visitedCharger: VisitedCharger): Charger {
    val addressInfo =
        Gson().fromJson(visitedCharger.addressInfo, AddressInfo::class.java)
    val statusType =
        Gson().fromJson(visitedCharger.statusType, StatusType::class.java)
    val connections = getObject<ArrayList<Connection>>(visitedCharger.connections!!)
    val operatorInfo = Gson().fromJson(visitedCharger.operatorInfo, OperatorInfo::class.java)
    val usageType = Gson().fromJson(visitedCharger.usageType, UsageType::class.java)

    return Charger(
        visitedCharger.id,
        addressInfo,
        statusType,
        connections,
        usageType,
        operatorInfo
    )

}

inline fun <reified T> getObject(json: String): T {
    val type = object : TypeToken<T>() {}.type
    return Gson().fromJson(json, type)
}