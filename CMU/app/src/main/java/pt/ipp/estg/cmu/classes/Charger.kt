package pt.ipp.estg.cmu.classes

import androidx.room.Entity
import androidx.room.PrimaryKey
import pt.ipp.estg.cmu.api.openchargemap.*

@Entity
data class Charger(
    val id: Int,
    val addressInfo: AddressInfo,
    val status: StatusType,
    val connections: ArrayList<Connections> = arrayListOf(),
    val usageCost: String?,
    val operatorInfo: OperatorInfo,
)
