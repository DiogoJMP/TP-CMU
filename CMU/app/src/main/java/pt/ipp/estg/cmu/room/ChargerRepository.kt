package pt.ipp.estg.cmu.room

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import java.util.*

class ChargerRepository(private val chargerDAO: ChargerDAO) {
    fun getChargers(): LiveData<List<ChargerEntity>> {
        return chargerDAO.getChargers()
    }

    suspend fun insertChargers(chargers: List<ChargerEntity>) {
        return chargerDAO.insertChargers(chargers)
    }
}