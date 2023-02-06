package pt.ipp.estg.tp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import io.github.dellisd.spatialk.geojson.FeatureCollection
import pt.ipp.estg.tp.api.geoapify.GeoapifyAPI
import pt.ipp.estg.tp.api.geoapify.GeoapifyResponse
import pt.ipp.estg.tp.api.openchargemap.OCMResponse
import pt.ipp.estg.tp.api.openchargemap.OpenChargeMapAPI
import pt.ipp.estg.tp.retrofit.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val openChargeMapAPI = RetrofitHelper(
            OCM_URL
        ).getInstance().create(OpenChargeMapAPI::class.java)
        val geoapifyAPI = RetrofitHelper(
            GEOAPIFY_URL
        ).getInstance().create(GeoapifyAPI::class.java)
        geoapifyAPI.getPlacesCategories(
            "accommodation",
            "circle:-0.07071648508463113,51.50848194136378,1000",
            "-0.07071648508463113,51.50848194136378"
        )
            .enqueue(object : Callback<FeatureCollection> {
                override fun onResponse(
                    call: Call<FeatureCollection>,
                    response: Response<FeatureCollection>
                ) {
                    val places = response.body()
                    if (places != null) {
                        Log.d("Sites", places.toString())
                    } else {
                        Log.d("Status", "Null")
                    }
                }

                override fun onFailure(call: Call<FeatureCollection>, t: Throwable) {
                    Log.d("Error", "Fodeu")
                }
            })

        openChargeMapAPI.getSites(10, "PT", 41.3680600, -8.1939600)
            .enqueue(object : Callback<List<OCMResponse>> {
                override fun onResponse(
                    call: Call<List<OCMResponse>>,
                    response: Response<List<OCMResponse>>
                ) {
                    val sites = response.body()
                    if (sites != null) {
                        Log.d("Sites", sites.toString())
                    } else {
                        Log.d("Status", "Null")
                    }
                }

                override fun onFailure(call: Call<List<OCMResponse>>, t: Throwable) {
                    Log.d("Error", "Fodeu")
                }
            })
    }
}


