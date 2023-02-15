package pt.ipp.estg.cmu.room

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChargerUserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsFavorite(charger: ChargerUser)

    @RequiresApi(Build.VERSION_CODES.O)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsHistory(charger: ChargerUser)

    @Query("SELECT * FROM ChargerUser WHERE type=:type AND userId=:userId")
    fun getFavorites(userId: String, type: String = "favorites"): LiveData<List<ChargerUser>>

    @Query("SELECT * FROM ChargerUser WHERE type=:type AND userId=:userId ORDER BY timeVisited DESC")
    fun getHistory(
        userId: String,
        type: String = "history"
    ): LiveData<List<ChargerUser>>
}