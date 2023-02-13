package pt.ipp.estg.cmu.room

import androidx.lifecycle.LiveData

class ChargerRepository(private val chargerDAO: ChargerDAO) {
    fun getChargersByUserId(userId: String): LiveData<List<ChargerEntity>> {
        return chargerDAO.getChargersByUserId(userId)
    }

    suspend fun insertAsFavorite(chargerEntity: ChargerEntity) {
        return chargerDAO.insertAsFavorite(chargerEntity)
    }

    suspend fun insertAsHistory(chargerEntity: ChargerEntity) {
        return chargerDAO.insertAsHistory(chargerEntity)
    }

    fun getFavorites(userId: String): LiveData<List<ChargerEntity>> {
        return chargerDAO.getFavorites(userId)
    }

    fun getHistory(userId: String): LiveData<List<ChargerEntity>> {
        return chargerDAO.getHistory(userId)
    }
}