package pe.edu.upc.upet.feature_petOwner.data.remote

import com.google.gson.annotations.SerializedName

typealias PetOwnerResponseList = List<PetOwnerResponse>

enum class SubscriptionType {
    @SerializedName("Basic")
    BASIC,
    @SerializedName("Advanced")
    ADVANCED,
}

data class PetOwnerResponse (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("numberPhone")
    val numberPhone: String,
    @SerializedName("image_url")
    val image_url: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("subscriptionType")
    val subscriptionType: SubscriptionType
)