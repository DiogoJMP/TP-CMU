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
import pt.ipp.estg.cmu.classes.VisitedCharger

class VisitedCollection(
    private val firestore: FirebaseFirestore = Firebase.firestore,
    private val auth: FirebaseAuth = Firebase.auth
) {
    private val user = auth.currentUser!!.uid
    val visited: Flow<List<VisitedCharger>> =
        firestore.collection("users")
            .document(user)
            .collection("visited")
            .snapshots()
            .map { snapshot -> snapshot.toObjects() }

    suspend fun add(charger: VisitedCharger) {
        firestore.collection("users")
            .document(user)
            .collection("visited")
            .add(charger)
            .await()
    }
}