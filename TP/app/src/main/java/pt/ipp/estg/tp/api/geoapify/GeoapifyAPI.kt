package pt.ipp.estg.tp.api.geoapify

import io.github.dellisd.spatialk.geojson.FeatureCollection
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoapifyAPI {
    @GET("places?apiKey=793c002378be4d9297b29f8f91cce826")
    fun getPlaces(
        @Query("filter") filter: String,
        @Query("bias") bias: String,
    ): Call<List<GeoapifyResponse>>

    @GET("places?apiKey=793c002378be4d9297b29f8f91cce826")
    fun getPlacesCategories(
        @Query("categories") categories: String,
        @Query("filter") filter: String,
        @Query("bias") bias: String,
    ): Call<FeatureCollection>

}