package pe.edu.upc.upet.feature_smartCollar.data.remote

import pe.edu.upc.upet.feature_medicalHistory.data.remote.MedicalHistoryRequest
import pe.edu.upc.upet.feature_pet.data.remote.PetRequest
import pe.edu.upc.upet.feature_pet.data.remote.PetResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SmartCollarService {
    @GET("smart-collars")
    fun getAllSmartsCollars(): Call<List<SmartCollarResponse>>

    @POST("add_smart-collar")
    fun createSmartCollar(@Body smartCollarRequest: SmartCollarRequest): Call<SmartCollarResponse>

    @PUT("change_pet_association/{collar_id}/{new_pet_id}")
    fun changePetAssociation(
        @Path("collar_id") collar_id: Int,
        @Path("new_pet_id") new_pet_id: Int
    ): Call<SmartCollarResponse>

    @GET("smart-collars/pet/{pet_id}")
    fun getSmartCollarsByPetId(@Path("pet_id") pet_id: Int): Call<List<SmartCollarResponse>>


}