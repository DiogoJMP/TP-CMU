package pt.ipp.estg.cmu.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import pt.ipp.estg.cmu.classes.Charger
import pt.ipp.estg.cmu.room.ChargerRepository
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChargerList(
    chargers: SnapshotStateList<Charger>,
    paddingValues: PaddingValues,
    chargerRepository: ChargerRepository,
    auth: FirebaseAuth,
    flag: String
) {

    val dialogState = rememberSaveable { (mutableStateOf(false)) }
    val selectedCard = remember { (mutableStateOf(0)) }

    LazyColumn(Modifier.padding(paddingValues)) {
        items(chargers.size) { index ->
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
                            charger = chargers[selectedCard.value],
                            chargerRepository = chargerRepository,
                            auth = auth,
                            dialogState = dialogState,
                            flag = flag
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                ) {
                    chargers[index].addressInfo.Title?.let { Text(it) }
                    chargers[index].addressInfo.AddressLine1?.let { Text(it) }
                    chargers[index].status.Title?.let { Text(it) }
                    if (chargers[index].timeVisited?.time != 0L) {
                        chargers[index].timeVisited?.let {
                            Text(
                                SimpleDateFormat("dd/MM/yy HH:mm").format(
                                    it
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
