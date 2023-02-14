package pt.ipp.estg.cmu.classes

import android.location.Location
import android.util.Log
import pt.ipp.estg.cmu.OCM_URL
import pt.ipp.estg.cmu.api.openchargemap.OCMResponse
import pt.ipp.estg.cmu.api.openchargemap.OpenChargeMapAPI
import pt.ipp.estg.cmu.retrofit.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OCMLookup {
    fun chargerLookUp(
        currentLocation: Location?,
        apiResponse: (data: ArrayList<Charger>?, error: Throwable?) -> Unit
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
                val chargersList: ArrayList<Charger> = arrayListOf()
                val chargers = response.body()
                if (chargers != null) {
                    for (charger in chargers) {
                        chargersList.add(
                            Charger(
                                charger.ID,
                                charger.AddressInfo,
                                charger.StatusType,
                                charger.Connections,
                                charger.UsageType,
                                charger.OperatorInfo,
                                null
                            )
                        )
                    }
                    apiResponse(chargersList, null)
                } else {
                    apiResponse(null, Throwable("An error occurred"))
                }
            }

            override fun onFailure(call: Call<List<OCMResponse>>, t: Throwable) {
                Log.d("Error", t.message, t)
            }
        })
    }
}