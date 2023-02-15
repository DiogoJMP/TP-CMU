package pt.ipp.estg.cmu.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import pt.ipp.estg.cmu.classes.FavoriteCharger
import pt.ipp.estg.cmu.firestore.FavoritesCollection
import pt.ipp.estg.cmu.room.ChargerRoomDB
import pt.ipp.estg.cmu.room.ChargerUserRepository

class FavoritesVM(application: Application) :
    AndroidViewModel(application) {
    private val collection = FavoritesCollection()

    fun addFavorite(charger: FavoriteCharger, toast: Toast) {
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

    fun getFavorites(): Flow<List<FavoriteCharger>> {
        return collection.favorites
    }
}