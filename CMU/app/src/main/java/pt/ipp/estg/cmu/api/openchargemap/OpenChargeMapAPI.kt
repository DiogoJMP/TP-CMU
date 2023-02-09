package pt.ipp.estg.cmu.api.openchargemap

import pt.ipp.estg.cmu.api.OCM_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenChargeMapAPI {
    @GET("poi/?key=${OCM_KEY}&output=json")
    fun getSites(
        @Query("maxresults") maxresults: Number,
        @Query("countrycode") countrycode: String,
        @Query("latitude") latitude: Number,
        @Query("longitude") longitude: Number
    ): Call<List<OCMResponse>>
}