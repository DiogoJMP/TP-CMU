package pt.ipp.estg.cmu.classes

import pt.ipp.estg.cmu.api.openchargemap.*
import java.util.*
import kotlin.collections.ArrayList

data class Charger(
    val id: Int,
    val addressInfo: AddressInfo,
    val status: StatusType,
    val connections: ArrayList<Connection> = arrayListOf(),
    val usageType: UsageType,
    val operatorInfo: OperatorInfo,
    val timeVisited: Date?
)
