package pt.ipp.estg.tp.api.geoapify

import com.google.gson.annotations.SerializedName

data class GeoapifyResponse(
    @SerializedName("type") val type: String,
    @SerializedName("features") val features: ArrayList<Feature> = arrayListOf()
)

data class Feature(
    @SerializedName("type") val type: String? = null,
    @SerializedName("properties") val properties: Properties? = Properties(),
    @SerializedName("geometry") val geometry: Geometry? = Geometry()
)

data class Geometry(
    @SerializedName("type") val type: String? = null,
    @SerializedName("coordinates") val coordinates: ArrayList<Number> = arrayListOf()
)

data class Properties(
    @SerializedName("name") val name: String? = null,
    @SerializedName("country") val country: String? = null,
    @SerializedName("country_code") val countryCode: String? = null,
    @SerializedName("state") val state: String? = null,
    @SerializedName("county") val county: String? = null,
    @SerializedName("city") val city: String? = null,
    @SerializedName("postcode") val postcode: String? = null,
    @SerializedName("district") val district: String? = null,
    @SerializedName("neighbourhood") val neighbourhood: String? = null,
    @SerializedName("suburb") val suburb: String? = null,
    @SerializedName("street") val street: String? = null,
    @SerializedName("lon") val lon: Number? = null,
    @SerializedName("lat") val lat: Number? = null,
    @SerializedName("state_code") val stateCode: String? = null,
    @SerializedName("formatted") val formatted: String? = null,
    @SerializedName("address_line1") val addressLine1: String? = null,
    @SerializedName("address_line2") val addressLine2: String? = null,
    @SerializedName("categories") val categories: ArrayList<String> = arrayListOf(),
    @SerializedName("details") val details: ArrayList<String> = arrayListOf(),
    @SerializedName("distance") val distance: Int? = null,
    @SerializedName("place_id") val placeId: String? = null
)