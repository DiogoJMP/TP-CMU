package pt.ipp.estg.cmu.viewmodels

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationVM : ViewModel() {
    private val locationMutable = MutableLiveData<Location>()
    val location: LiveData<Location>
        get() = locationMutable

    fun updateLocation(location: Location) {
        locationMutable.value = location
    }
}