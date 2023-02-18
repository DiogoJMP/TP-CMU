package pt.ipp.estg.cmu.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import pt.ipp.estg.cmu.api.openchargemap.*

@Entity
data class ChargerEntity(
    @PrimaryKey val id: Int,
    val addressInfo: String,
    val statusType: String,
    val connections: String,
    val usageType: String,
    val operatorInfo: String,
    val distance: Double,
)