package pt.ipp.estg.cmu.classes

import com.google.firebase.firestore.DocumentId

data class FavoriteCharger(
    @DocumentId val id: String = "",
    val addressInfo: String? = null,
    val statusType: String? = null,
    val connections: String? = null,
    val usageType: String? = null,
    val operatorInfo: String? = null,
)
