package pt.ipp.estg.cmu.classes

import pt.ipp.estg.cmu.api.openchargemap.*
import kotlin.collections.ArrayList

data class Charger(
    val id: String,
    val addressInfo: AddressInfo,
    val status: StatusType,
    val connections: ArrayList<Connection> = arrayListOf(),
    val usageType: UsageType,
    val operatorInfo: OperatorInfo
)
