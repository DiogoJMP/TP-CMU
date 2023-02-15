package pt.ipp.estg.cmu.composables.screens

import android.os.Build
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
import com.google.gson.reflect.TypeToken
import pt.ipp.estg.cmu.api.openchargemap.*
import pt.ipp.estg.cmu.classes.Charger
import pt.ipp.estg.cmu.classes.VisitedCharger
import pt.ipp.estg.cmu.composables.ChargerDetailsDialog
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
    val dialogState = rememberSaveable { (mutableStateOf(false)) }
    val selectedCard = remember { (mutableStateOf(0)) }
    when {
        history.isEmpty() -> Text(
            modifier = Modifier.fillMaxWidth(),
            text = "You haven't visited any chargers yet"
        )
        else -> LazyColumn() {
            items(history.size) { index ->
                val charger = visitedChargerToCharger(history[index])
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
                                charger = visitedChargerToCharger(history[selectedCard.value]),
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
                        charger.addressInfo.AddressLine1?.let { Text(it) }
                        charger.status.Title?.let { Text(it) }
                        Text(SimpleDateFormat("dd/MM/yy HH:mm").format(history[index].timeVisited))
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