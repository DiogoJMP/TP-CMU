package pt.ipp.estg.cmu.firestore

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import pt.ipp.estg.cmu.classes.FavoriteCharger

class FavoritesCollection(
    private val firestore: FirebaseFirestore = Firebase.firestore,
    private val auth: FirebaseAuth = Firebase.auth
) {
    private val user = auth.currentUser!!.uid
    val favorites: Flow<List<FavoriteCharger>> =
        firestore.collection("users")
            .document(user)
            .collection("favorites")
            .snapshots()
            .map { snapshot -> snapshot.toObjects() }

    suspend fun add(charger: FavoriteCharger) {
        firestore.collection("users")
            .document(user)
            .collection("favorites")
            .document(charger.id)
            .set(charger)
            .await()
    }

    suspend fun remove(id: String) {
        firestore.collection("users")
            .document(user)
            .collection("favorites")
            .document(id)
            .delete()
            .await()
    }

    suspend fun isFavorite(id: String): Boolean {
        return firestore.collection("users")
            .document(user)
            .collection("favorites")
            .document(id)
            .get().await().exists()
    }
}