package pt.ipp.estg.cmu.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pt.ipp.estg.cmu.classes.ChargerRating
import pt.ipp.estg.cmu.firestore.Ratings

class RatingsVM(application: Application) : AndroidViewModel(application) {
    private val ratings = Ratings()

    fun rateCharger(chargerId: String, rating: Double, toast: Toast) {
        viewModelScope.launch {
            ratings.rateCharger(chargerId, rating)
            toast.show()
        }

    }

    suspend fun getRating(id: String): ChargerRating? {
        return ratings.getRatingById(id)
    }
}