package pt.ipp.estg.cmu.composables.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.type.DateTime
import pt.ipp.estg.cmu.api.openchargemap.*
import pt.ipp.estg.cmu.classes.Charger
import pt.ipp.estg.cmu.composables.ChargerList
import pt.ipp.estg.cmu.room.ChargerEntity
import pt.ipp.estg.cmu.room.ChargerRepository
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryScreen(
    paddingValues: PaddingValues,
    chargerRepository: ChargerRepository,
    auth: FirebaseAuth
) {
    val chargerHistory: LiveData<List<ChargerEntity>> =
        chargerRepository.getHistory(auth.currentUser!!.uid)


    val chargersList = remember { mutableStateListOf<Charger>() }

    chargerHistory.observeForever {
        for (charger in it) {
            val addressInfo =
                Gson().fromJson(charger.addressInfo, AddressInfo::class.java)
            val statusType =
                Gson().fromJson(charger.statusType, StatusType::class.java)
            val connections = getObject<ArrayList<Connection>>(charger.connections)
            val timeVisited = Date(charger.timeVisited)
            val operatorInfo = Gson().fromJson(charger.operatorInfo, OperatorInfo::class.java)
            val usageType = Gson().fromJson(charger.usageType, UsageType::class.java)

            val addedCharger = Charger(
                charger.id,
                addressInfo,
                statusType,
                connections,
                usageType,
                operatorInfo,
                timeVisited
            )

            if (!chargersList.contains(addedCharger))
                chargersList.add(addedCharger)
        }
    }

    ChargerList(
        chargers = chargersList,
        paddingValues = paddingValues,
        chargerRepository = chargerRepository,
        auth = auth,
        flag = "history"
    )
}

inline fun <reified T> getObject(json: String): T {
    val type = object : TypeToken<T>() {}.type
    return Gson().fromJson(json, type)
}