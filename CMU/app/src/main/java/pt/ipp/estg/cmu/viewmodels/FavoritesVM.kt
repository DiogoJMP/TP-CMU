package pt.ipp.estg.cmu.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import pt.ipp.estg.cmu.classes.FavoritedCharger
import pt.ipp.estg.cmu.firestore.FavoritesCollection
import pt.ipp.estg.cmu.room.ChargerRoomDB
import pt.ipp.estg.cmu.room.ChargerUser
import pt.ipp.estg.cmu.room.ChargerUserRepository

class FavoritesVM(application: Application) :
    AndroidViewModel(application) {
    private val chargerRepository: ChargerUserRepository
    private val collection = FavoritesCollection()

    init {
        val chargerDB = ChargerRoomDB.getInstance(application)
        val chargerUserDAO = chargerDB.chargerUserDAO()
        chargerRepository = ChargerUserRepository(chargerUserDAO)
    }

    fun addFavorite(charger: FavoritedCharger, toast: Toast) {
        viewModelScope.launch {
            if (collection.isFavorite(charger.id)) {
                collection.remove(charger.id)
                toast.setText("Removed from favorites")
                toast.show()
            } else {
                collection.add(charger)
                toast.show()
            }
        }
    }

    fun getFavorites(): Flow<List<FavoritedCharger>> {
        return collection.favorites
    }
}