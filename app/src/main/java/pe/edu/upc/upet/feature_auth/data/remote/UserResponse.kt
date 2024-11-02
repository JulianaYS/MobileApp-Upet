package pe.edu.upc.upet.feature_auth.data.remote

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("userType")
    val userType: UserType,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("registered")
    val registered: Boolean
)