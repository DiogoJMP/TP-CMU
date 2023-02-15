package pt.ipp.estg.cmu.room

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData

class ChargerUserRepository(private val chargerUserDAO: ChargerUserDAO) {
    suspend fun insertAsFavorite(chargerUser: ChargerUser) {
        return chargerUserDAO.insertAsFavorite(chargerUser)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun insertAsHistory(chargerUser: ChargerUser) {
        return chargerUserDAO.insertAsHistory(chargerUser)
    }

    fun getFavorites(userId: String, type: String = "favorites"): LiveData<List<ChargerUser>> {
        return chargerUserDAO.getFavorites(userId, type)
    }

    fun getHistory(userId: String, type: String = "history"): LiveData<List<ChargerUser>> {
        return chargerUserDAO.getHistory(userId, type)
    }
}