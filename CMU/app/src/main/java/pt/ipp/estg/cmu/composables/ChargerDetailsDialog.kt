package pt.ipp.estg.cmu.composables

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import pt.ipp.estg.cmu.api.openchargemap.Connection
import pt.ipp.estg.cmu.classes.Charger
import pt.ipp.estg.cmu.room.ChargerRepository
import pt.ipp.estg.cmu.ui.theme.Purple40
import pt.ipp.estg.cmu.viewmodels.FavoritesVM
import pt.ipp.estg.cmu.viewmodels.HistoryVM

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChargerDetailsDialog(
    charger: Charger,
    favoritesVM: FavoritesVM = viewModel(),
    historyVM: HistoryVM = viewModel(),
    auth: FirebaseAuth,
    dialogState: MutableState<Boolean>,
    flag: String
) {
    AlertDialog(
        onDismissRequest = { dialogState.value = false },
        title = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
            ) {
                charger.addressInfo.Title?.let {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = it
                    )
                }
            }
        },
        text = {
            Column(modifier = Modifier.padding(PaddingValues(3.dp))) {
                charger.operatorInfo.Title?.let {
                    Text(
                        text = "Operator: $it"
                    )
                }

                charger.addressInfo.AddressLine1?.let {
                    Text(
                        "Address: $it"
                    )
                }
                charger.status.Title?.let { Text("Status: $it") }
                charger.usageType.Title?.let { Text("Usage: $it") }
                Text("Connections: ")
                ConnectionsList(
                    connections = charger.connections,
                )
            }
        },
        buttons = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                val context = LocalContext.current
                DialogButton(
                    charger = charger,
                    type = "favorites",
                    context = context
                )
                if (flag != "history") {
                    Spacer(modifier = Modifier.size(3.dp))
                    DialogButton(
                        charger = charger,
                        type = "history",
                        context = context
                    )
                }
                Spacer(modifier = Modifier.size(3.dp))
                Button(
                    onClick = {
                        val gmmIntentUri =
                            Uri.parse(
                                "geo:0,0?q=${charger.addressInfo.Latitude}," +
                                        "${charger.addressInfo.Longitude}"
                            )
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        ContextCompat.startActivity(context, mapIntent, null)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Map,
                        contentDescription = "To Google Maps"
                    )
                }
            }
        }
    )
}

@Composable
fun ConnectionsList(connections: ArrayList<Connection>) {
    LazyColumn(
        modifier = Modifier
            .padding(PaddingValues(3.dp))
            .border(
                BorderStroke(
                    3.dp,
                    Purple40
                ),
                RoundedCornerShape(8)
            )
    ) {
        items(connections.size) { index ->
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                connections[index].ConnectionType?.Title?.let { Text("Type: $it") }
                connections[index].StatusType?.Title?.let { Text("Status: $it") }
                connections[index].Level?.Title?.let { Text("Level: $it") }
                connections[index].Amps?.let { Text("Amps: $it") }
                connections[index].Voltage?.let { Text("Voltage: $it") }
                connections[index].PowerKW?.let { Text("PowerKW: $it") }
            }
        }
    }
}
