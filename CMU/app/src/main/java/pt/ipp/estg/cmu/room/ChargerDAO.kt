package pt.ipp.estg.cmu.room

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.time.LocalDateTime

@Dao
interface ChargerDAO {

    @Query("SELECT * FROM ChargerEntity")
    fun getChargers(): LiveData<List<ChargerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChargers(chargers: List<ChargerEntity>)

}