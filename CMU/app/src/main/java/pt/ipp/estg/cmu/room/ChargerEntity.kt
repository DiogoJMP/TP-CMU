package pt.ipp.estg.cmu.room

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import pt.ipp.estg.cmu.api.openchargemap.*

@Entity(primaryKeys = ["id", "userId"])
data class ChargerEntity(
    val id: Int,
    val userId: String,
    val addressInfo: String,
    val statusType: String,
    val connections: String,
    val usageCost: String,
    val operatorInfo: String,
    val type: String,
)