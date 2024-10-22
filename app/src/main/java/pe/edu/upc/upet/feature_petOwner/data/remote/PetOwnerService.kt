package pe.edu.upc.upet.feature_petOwner.data.remote

import pe.edu.upc.upet.feature_auth.data.remote.SignInResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PetOwnerService {
    companion object {
        const val BASE_ENDPOINT = "petOwners/"
    }

    @GET("petowners/")
    fun getAll(): Call<PetOwnerResponseList>

    @GET("petowners/users/{user_id}")
    fun getByUserId(@Path("user_id") userId: Int): Call<PetOwnerResponse>

    @GET("petowners/{petOwner_id}")
    fun getById(@Path("petOwner_id") id: Int): Call<PetOwnerResponse>

    @POST("petowners/{user_id}")
    fun createPetOwner(
        @Path("user_id") userId: Int,
        @Body petOwnerData: PetOwnerRequest
    ): Call<SignInResponse>

    @PUT("petowners/{petOwner_id}")
    fun updatePetOwner(
        @Path("petOwner_id") petOwnerId: Int,
        @Body petOwnerData: EditPetOwnerRequest
    ): Call<PetOwnerResponse>
}