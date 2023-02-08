package pt.ipp.estg.cmu.api.openchargemap

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenChargeMapAPI {
    @GET("poi/?key=6babce48-0973-49af-937b-3f3a1c76ae30&output=json")
    fun getSites(
        @Query("maxresults") maxresults: Number,
        @Query("countrycode") countrycode: String,
        @Query("latitude") latitude: Number,
        @Query("longitude") longitude: Number
    ): Call<List<OCMResponse>>
}