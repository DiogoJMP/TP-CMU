package pt.ipp.estg.cmu.room


import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import pt.ipp.estg.cmu.api.openchargemap.*

@Entity(primaryKeys = ["id", "userId", "timeVisited"])
data class ChargerUser(
    val id: Int,
    val userId: String,
    val addressInfo: String,
    val statusType: String,
    val connections: String,
    val usageType: String,
    val operatorInfo: String,
    val type: String,
    val timeVisited: Long,
)