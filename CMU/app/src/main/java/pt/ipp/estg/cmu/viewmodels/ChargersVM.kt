package pt.ipp.estg.cmu.viewmodels

import android.app.Application
import android.location.Location
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import pt.ipp.estg.cmu.classes.OCMLookup
import pt.ipp.estg.cmu.room.ChargerEntity
import pt.ipp.estg.cmu.room.ChargerRepository
import pt.ipp.estg.cmu.room.ChargerRoomDB

class ChargersVM(application: Application) :
    AndroidViewModel(application) {
    private val ocmLookup = OCMLookup()
    private val chargerRepository: ChargerRepository

    init {
        val chargerDB = ChargerRoomDB.getInstance(application)
        val chargerDAO = chargerDB.chargerDAO()
        chargerRepository = ChargerRepository(chargerDAO)
    }

    val chargerList: LiveData<List<ChargerEntity>>
        get() = chargerRepository.getChargers()

    fun fetchChargers(currentLocation: Location?) {
        ocmLookup.chargerLookUp(currentLocation = currentLocation) { chargers, _ ->
            viewModelScope.launch {
                chargers?.mapIndexed { i, charger ->
                    ChargerEntity(
                        id = charger.id.toInt(),
                        addressInfo = Gson().toJson(charger.addressInfo),
                        statusType = Gson().toJson(charger.status),
                        connections = Gson().toJson(charger.connections),
                        operatorInfo = Gson().toJson(charger.operatorInfo),
                        usageType = Gson().toJson(charger.usageType),
                        distance = charger.addressInfo.Distance!!
                    )
                }?.let {
                    chargerRepository.insertChargers(it)
                }
            }
        }
    }
    suspend fun clearCache(){
        chargerRepository.deleteChargers()
    }
}