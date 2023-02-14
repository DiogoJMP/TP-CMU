package pt.ipp.estg.cmu.composables.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import com.google.firebase.auth.FirebaseAuth
import pt.ipp.estg.cmu.classes.Charger
import pt.ipp.estg.cmu.classes.OCMLookup
import pt.ipp.estg.cmu.composables.ChargerList
import pt.ipp.estg.cmu.room.ChargerRepository
import pt.ipp.estg.cmu.viewmodels.LocationVM

@RequiresApi(Build.VERSION_CODES.O)
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
    ChargerList(chargers = chargersList, paddingValues, chargerRepository, auth, "else")
}
