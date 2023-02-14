package pt.ipp.estg.cmu.room

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import java.util.*

class ChargerRepository(private val chargerDAO: ChargerDAO) {
    fun getChargersByUserId(userId: String): LiveData<List<ChargerEntity>> {
        return chargerDAO.getChargersByUserId(userId)
    }

    suspend fun insertAsFavorite(chargerEntity: ChargerEntity) {
        return chargerDAO.insertAsFavorite(chargerEntity)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun insertAsHistory(chargerEntity: ChargerEntity) {
        return chargerDAO.insertAsHistory(chargerEntity)
    }

    fun getFavorites(userId: String, type: String = "favorites"): LiveData<List<ChargerEntity>> {
        return chargerDAO.getFavorites(userId, type)
    }

    fun getHistory(userId: String, type: String = "history"): LiveData<List<ChargerEntity>> {
        return chargerDAO.getHistory(userId, type)
    }
}