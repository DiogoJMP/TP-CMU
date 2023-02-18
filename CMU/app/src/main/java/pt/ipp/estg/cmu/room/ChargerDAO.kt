package pt.ipp.estg.cmu.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ChargerDAO {

    @Query("SELECT * FROM ChargerEntity ORDER BY distance ASC")
    fun getChargers(): LiveData<List<ChargerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChargers(chargers: List<ChargerEntity>)

    @Query("DELETE FROM ChargerEntity")
    suspend fun deleteChargers()

}