package pt.ipp.estg.cmu.classes

import com.google.firebase.firestore.DocumentId

class ChargerRating(
    @DocumentId internal var id: Int = 0,
    internal var averageScore: Double = 0.0,
    internal var totalRatings: Int = 0
) {
}