package pt.ipp.estg.cmu.composables.screens

import android.content.Intent
import android.location.Location
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Grade
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import pt.ipp.estg.cmu.MainActivity
import pt.ipp.estg.cmu.OCM_URL
import pt.ipp.estg.cmu.api.openchargemap.AddressInfo
import pt.ipp.estg.cmu.classes.Charger
import pt.ipp.estg.cmu.api.openchargemap.OCMResponse
import pt.ipp.estg.cmu.api.openchargemap.OpenChargeMapAPI
import pt.ipp.estg.cmu.classes.OCMLookup
import pt.ipp.estg.cmu.retrofit.RetrofitHelper
import pt.ipp.estg.cmu.room.ChargerEntity
import pt.ipp.estg.cmu.room.ChargerRepository
import pt.ipp.estg.cmu.viewmodels.LocationVM
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ChargersScreen(
    currentLocationViewModel: LocationVM,
    paddingValues: PaddingValues,
    chargerRepository: ChargerRepository,
    auth: FirebaseAuth
) {

    val currentLocation by currentLocationViewModel.location.observeAsState()
    val chargersList = remember { mutableStateListOf<Charger>() }
    OCMLookup().chargerLookUp(currentLocation) { data, error ->
        if (error == null) {
            chargersList.clear()
            chargersList.addAll(data!!)
        }
    }
    ChargersList(chargers = chargersList, paddingValues, chargerRepository, auth)
}

@Composable
fun ChargersList(
    chargers: SnapshotStateList<Charger>,
    paddingValues: PaddingValues,
    chargerRepository: ChargerRepository,
    auth: FirebaseAuth
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
                        AlertDialog(
                            onDismissRequest = { dialogState.value = false },
                            title = {
                                chargers[selectedCard.value].addressInfo.Title?.let {
                                    Text(
                                        it
                                    )
                                }
                            },
                            text = {
                                Column {
                                    chargers[selectedCard.value].addressInfo.AddressLine1?.let {
                                        Text(
                                            it
                                        )
                                    }
                                    chargers[selectedCard.value].status.Title?.let { Text(it) }
                                }
                            },
                            buttons = {
                                Row {
                                    CardButton(
                                        charger = chargers[index],
                                        chargerRepository = chargerRepository,
                                        auth = auth,
                                        type = "favorites"
                                    )
                                    CardButton(
                                        charger = chargers[index],
                                        chargerRepository = chargerRepository,
                                        auth = auth,
                                        type = "history"
                                    )
                                    val context = LocalContext.current
                                    Button(
                                        onClick = {
                                            val gmmIntentUri =
                                                Uri.parse(
                                                    "geo:0,0?q=${chargers[selectedCard.value].addressInfo.Latitude}," +
                                                            "${chargers[selectedCard.value].addressInfo.Longitude}"
                                                )
                                            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                            mapIntent.setPackage("com.google.android.apps.maps")
                                            startActivity(context, mapIntent, null)
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
                }
                Column(
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                ) {
                    chargers[index].addressInfo.Title?.let { Text(it) }
                    chargers[index].addressInfo.AddressLine1?.let { Text(it) }
                    chargers[index].status.Title?.let { Text(it) }
                }
            }
        }
    }
}

@Composable
fun CardButton(
    charger: Charger,
    chargerRepository: ChargerRepository,
    auth: FirebaseAuth,
    type: String
) {

    val coroutineScope = rememberCoroutineScope()
    val gsonAddress = Gson().toJson(charger.addressInfo)
    val gsonStatus = Gson().toJson(charger.status)
    val gsonConnections = Gson().toJson(charger.connections)
    val gsonOperator = Gson().toJson(charger.operatorInfo)


    Button(onClick = {
        if (type == "favorites") {
            coroutineScope.launch {
                charger.usageCost?.let {
                    ChargerEntity(
                        charger.id,
                        auth.currentUser!!.uid,
                        gsonAddress,
                        gsonStatus,
                        gsonConnections,
                        it,
                        gsonOperator,
                        type
                    )
                }?.let {
                    chargerRepository.insertAsFavorite(
                        it
                    )
                }
                //val what = Gson().fromJson(gsonAddress, AddressInfo::class.java)
            }
        } else {
            coroutineScope.launch {
                charger.usageCost?.let {
                    ChargerEntity(
                        charger.id,
                        auth.currentUser!!.uid,
                        gsonAddress,
                        gsonStatus,
                        gsonConnections,
                        it,
                        gsonOperator,
                        type
                    )
                }?.let {
                    chargerRepository.insertAsHistory(
                        it
                    )
                }
                //val what = Gson().fromJson(gsonAddress, AddressInfo::class.java)
            }
        }
    }) {
        Icon(
            imageVector = if (type == "favorites") Icons.Outlined.Grade else Icons.Outlined.Check,
            contentDescription = type
        )
    }
}