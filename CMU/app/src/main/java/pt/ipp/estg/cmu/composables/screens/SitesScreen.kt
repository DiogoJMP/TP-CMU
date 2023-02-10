package pt.ipp.estg.cmu.composables.screens

import android.location.Location
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pt.ipp.estg.cmu.OCM_URL
import pt.ipp.estg.cmu.Site
import pt.ipp.estg.cmu.api.openchargemap.OCMResponse
import pt.ipp.estg.cmu.api.openchargemap.OpenChargeMapAPI
import pt.ipp.estg.cmu.retrofit.RetrofitHelper
import pt.ipp.estg.cmu.viewmodels.LocationVM
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun SitesScreen(currentLocationViewModel: LocationVM, paddingValues: PaddingValues) {

    val currentLocation by currentLocationViewModel.location.observeAsState()
    val sitesList = remember { mutableStateListOf<Site>() }
    siteLookUp(currentLocation) { data, error ->
        if (error == null) {
            sitesList.clear()
            sitesList.addAll(data!!)
        }
    }
    SitesList(sites = sitesList, paddingValues)
}

@Composable
fun SitesList(sites: SnapshotStateList<Site>, paddingValues: PaddingValues) {
    LazyColumn(Modifier.padding(paddingValues)) {
        items(sites.size) { index ->
            Card(
                border = BorderStroke(1.dp, Color.Blue),
                modifier = Modifier.padding(5.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                ) {
                    sites[index].addressInfo.Title?.let { Text(it) }
                    sites[index].addressInfo.AddressLine1?.let { Text(it) }
                    sites[index].status.Title?.let { Text(it) }
                }
            }
        }
    }
}

fun siteLookUp(
    currentLocation: Location?,
    apiResponse: (data: ArrayList<Site>?, error: Throwable?) -> Unit
) {
    val openChargeMapAPI = RetrofitHelper(
        OCM_URL
    ).getInstance().create(OpenChargeMapAPI::class.java)
    openChargeMapAPI.getSites(
        10, "PT",
        currentLocation?.latitude!!,
        currentLocation.longitude
    ).enqueue(object : Callback<List<OCMResponse>> {
        override fun onResponse(
            call: Call<List<OCMResponse>>,
            response: Response<List<OCMResponse>>
        ) {
            val sitesList: ArrayList<Site> = arrayListOf()
            val sites = response.body()
            if (sites != null) {
                for (site in sites) {
                    sitesList.add(Site(site.AddressInfo, site.StatusType))
                }
                apiResponse(sitesList, null)
            } else {
                apiResponse(null, Throwable("An error occurred"))
            }
        }

        override fun onFailure(call: Call<List<OCMResponse>>, t: Throwable) {
            Log.d("Error", t.message, t)
        }
    })
}