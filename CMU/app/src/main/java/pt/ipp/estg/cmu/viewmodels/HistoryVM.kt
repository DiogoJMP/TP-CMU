package pt.ipp.estg.cmu.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import pt.ipp.estg.cmu.classes.VisitedCharger
import pt.ipp.estg.cmu.firestore.VisitedCollection
import pt.ipp.estg.cmu.room.ChargerRoomDB
import pt.ipp.estg.cmu.room.ChargerUserRepository

class HistoryVM(application: Application) :
    AndroidViewModel(application) {
    private val collection = VisitedCollection()

    fun visitCharger(charger: VisitedCharger, toast: Toast) {
        viewModelScope.launch {
            collection.add(charger)
            toast.show()
        }
    }

    fun getVisited(): Flow<List<VisitedCharger>> {
        return collection.visited
    }
}