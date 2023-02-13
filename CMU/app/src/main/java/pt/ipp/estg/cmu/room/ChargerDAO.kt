package pt.ipp.estg.cmu.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChargerDAO {
    @Query("SELECT * FROM ChargerEntity WHERE userId=:userId")
    fun getChargersByUserId(userId: String): LiveData<List<ChargerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsFavorite(charger: ChargerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsHistory(charger: ChargerEntity)

    @Query("SELECT * FROM ChargerEntity WHERE type=:type AND userId=:userId")
    fun getFavorites(userId: String, type: String = "favorites"): LiveData<List<ChargerEntity>>


    @Query("SELECT * FROM ChargerEntity WHERE type=:type AND userId=:userId")
    fun getHistory(userId: String, type: String = "history"): LiveData<List<ChargerEntity>>

}