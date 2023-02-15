package pt.ipp.estg.cmu.classes

import com.google.firebase.firestore.DocumentId

data class VisitedCharger(
    @DocumentId val id: String = "",
    val addressInfo: String? = null,
    val statusType: String? = null,
    val connections: String? = null,
    val usageType: String? = null,
    val operatorInfo: String? = null,
    val timeVisited: Long? = null
)