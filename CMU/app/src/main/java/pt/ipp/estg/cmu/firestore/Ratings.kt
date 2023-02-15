package pt.ipp.estg.cmu.firestore

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import pt.ipp.estg.cmu.classes.ChargerRating

class Ratings(
    private val firestore: FirebaseFirestore = Firebase.firestore,
) {
    suspend fun getRatingById(id: String): ChargerRating? {
        return firestore.collection("ratings")
            .document(id)
            .get().await().toObject()
    }

    fun rateCharger(chargerId: String, rating: Double): Task<Void> {
        val chargerDoc = firestore.collection("ratings").document(chargerId)
        return firestore.runTransaction { transaction ->
            val chargerRating = transaction.get(chargerDoc).toObject<ChargerRating>()
            val currentTotal = (chargerRating!!.averageScore * chargerRating!!.totalRatings)

            chargerRating.averageScore = (currentTotal + rating) / (chargerRating.totalRatings + 1)
            chargerRating.totalRatings++

            transaction.set(chargerDoc, chargerRating, SetOptions.merge())
            null
        }
    }
}