package pt.ipp.estg.cmu.api.geoapify

import pt.ipp.estg.cmu.api.GEOAPIFY_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoapifyAPI {
    @GET("places?apiKey=${GEOAPIFY_KEY}")
    fun getPlaces(
        @Query("filter") filter: String,
        @Query("bias") bias: String,
    ): Call<List<GeoapifyResponse>>

    @GET("places?apiKey=${GEOAPIFY_KEY}")
    fun getPlacesCategories(
        @Query("categories") categories: String,
        @Query("filter") filter: String,
        @Query("bias") bias: String,
    ): Call<List<GeoapifyResponse>>

}