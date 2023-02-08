package pt.ipp.estg.cmu

import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import pt.ipp.estg.cmu.api.openchargemap.OCMResponse
import pt.ipp.estg.cmu.api.openchargemap.OpenChargeMapAPI
import pt.ipp.estg.cmu.composables.SiteList
import pt.ipp.estg.cmu.retrofit.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun OCMRequest() {
    val openChargeMapAPI =
        RetrofitHelper(OCM_URL).getInstance().create(OpenChargeMapAPI::class.java)
    openChargeMapAPI.getSites(10, "PT", 41.3680600, -8.1939600)
        .enqueue(object : Callback<List<OCMResponse>> {
            override fun onResponse(
                call: Call<List<OCMResponse>>,
                response: Response<List<OCMResponse>>
            ) {
                var sitesList: ArrayList<Site> = arrayListOf()
                val sites = response.body()!!
                for (site in sites) {
                    sitesList.add(
                        Site(
                            site.AddressInfo,
                            site.StatusType
                        )
                    )
                }
                /**    setContent {
                SiteList(sites = sitesList)
                }**/
            }

            override fun onFailure(call: Call<List<OCMResponse>>, t: Throwable) {
                Log.d("Error", "Fodeu")
            }
        })
}

