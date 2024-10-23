package pe.edu.upc.upet.feature_vet.data.repository

import android.util.Log
import pe.edu.upc.upet.feature_auth.data.remote.SignInResponse
import pe.edu.upc.upet.feature_review.data.remote.ReviewResponse
import pe.edu.upc.upet.feature_review.data.remote.VetResponseWithReviews
import pe.edu.upc.upet.feature_vet.data.mapper.toDomainModel
import pe.edu.upc.upet.feature_vet.data.remote.AvailableTimesRequest
import pe.edu.upc.upet.feature_vet.data.remote.AvailableTimesResponse
import pe.edu.upc.upet.feature_vet.data.remote.VetRequest
import pe.edu.upc.upet.feature_vet.data.remote.VetResponse
import pe.edu.upc.upet.feature_vet.data.remote.VetResponseList
import pe.edu.upc.upet.feature_vet.data.remote.VetService
import pe.edu.upc.upet.feature_vet.data.remote.VetServiceFactory
import pe.edu.upc.upet.feature_vet.data.remote.VetUpdateRequest
import pe.edu.upc.upet.feature_vet.domain.Vet
import pe.edu.upc.upet.feature_vet.domain.VetList
import pe.edu.upc.upet.utils.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VetRepository (
    private val vetService: VetService = VetServiceFactory.getVetServiceFactory())
{
    fun getVets(callback: (VetList) -> Unit){
        vetService.getVets().enqueue(object : Callback<VetResponseList> {
            override fun onResponse(
                call: Call<VetResponseList>,
                response: Response<VetResponseList>
            ) {
                if (response.isSuccessful){
                    val vets = response.body()?.map { it.toDomainModel() }?: emptyList()
                    callback(vets)
                }else{
                    Log.e("VetRepository", "Failed to get vets: ${response.errorBody()}")
                }
            }
            override fun onFailure(call: Call<VetResponseList>, t: Throwable) {
                Log.e("VetRepository", "Failed to get vets", t)
            }
        })
    }


    fun getVetById(vetId: Int, callback: (Vet?) -> Unit){
        vetService.getVetById(vetId).enqueue(object : Callback<VetResponse> {
            override fun onResponse(
                call: Call<VetResponse>,
                response: Response<VetResponse>
            ) {
                if (response.isSuccessful){
                    val vet = response.body()?.toDomainModel()
                    callback(vet)
                }else{
                    callback(null)
                }
            }
            override fun onFailure(call: Call<VetResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun getVetReviews(vetId: Int, onSuccess : (List<ReviewResponse>) -> Unit, onError: () -> Unit){
        vetService.getVetReviews(vetId).enqueue(object : Callback<VetResponseWithReviews> {
            override fun onResponse(
                call: Call<VetResponseWithReviews>,
                response: Response<VetResponseWithReviews>
            ) {
                if (response.isSuccessful){
                    Log.e("VetRepository", "Response: ${response.body()}")
                    val reviews = response.body()?.reviews
                    if(reviews != null){
                        onSuccess(reviews)
                    }
                    else{
                        onError()
                    }
                }else{
                    onError()
                }
            }
            override fun onFailure(call: Call<VetResponseWithReviews>, t: Throwable) {
                onError()
            }
        })
    }

    fun createVet(userId: Int, vetData: VetRequest, callback: (Boolean) -> Unit){
        vetService.createVet(userId, vetData).enqueue(object : Callback<SignInResponse> {
            override fun onResponse(
                call: Call<SignInResponse>,
                response: Response<SignInResponse>
            ) {
                if (response.isSuccessful){
                    val access  = response.body()?.access_token
                    if (access != null){
                        TokenManager.clearToken()
                        TokenManager.saveToken(access)
                    }
                    callback(true)
                }else{
                    Log.e("CreateVet", "Unsuccessful response: ${response.code()}")
                    callback(false)
                }
            }
            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                callback(false)
            }
        })
    }

    fun getVetsByUserId(userId: Int, callback: (Vet?) -> Unit){
        vetService.getVetsByUserId(userId).enqueue(object : Callback<VetResponse> {
            override fun onResponse(
                call: Call<VetResponse>,
                response: Response<VetResponse>
            ) {
                if (response.isSuccessful){
                    val vet = response.body()?.toDomainModel()
                    callback(vet)
                }else{
                    callback(null)
                }
            }
            override fun onFailure(call: Call<VetResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun getVetsByClinicId(clinicId: Int, callback: (VetList) -> Unit){
        vetService.getVetsByClinicId(clinicId).enqueue(object : Callback<List<VetResponse>> {
            override fun onResponse(
                call: Call<List<VetResponse>>,
                response: Response<List<VetResponse>>
            ) {
                if (response.isSuccessful){
                    val vets = response.body()?.map { it.toDomainModel() }?: emptyList()
                    callback(vets)
                }else{
                    Log.e("VetRepository", "Failed to get vets: ${response.errorBody()}")
                }
            }
            override fun onFailure(call: Call<List<VetResponse>>, t: Throwable) {
                Log.e("VetRepository", "Failed to get vets", t)
            }
        })
    }

    fun updateVet(vetId: Int, vetData: VetUpdateRequest, callback: (Boolean) -> Unit){
        vetService.updateVet(vetId, vetData).enqueue(object : Callback<VetResponse> {
            override fun onResponse(
                call: Call<VetResponse>,
                response: Response<VetResponse>
            ) {
                if (response.isSuccessful){
                    callback(true)
                }else{
                    Log.e("UpdateVet", "Unsuccessful response: ${response.message()}")
                    callback(false)
                }
            }
            override fun onFailure(call: Call<VetResponse>, t: Throwable) {
                Log.e("UpdateVet", "Failed to update vet", t)
                callback(false)
            }
        })
    }

    fun getAvailableTimes(vetId: Int, timeRequest: AvailableTimesRequest, callback: (List<String>?) -> Unit){
        vetService.getAvailableTimes(vetId, timeRequest).enqueue(object :
            Callback<AvailableTimesResponse> {
            override fun onResponse(
                call: Call<AvailableTimesResponse>,
                response: Response<AvailableTimesResponse>
            ) {
                if (response.isSuccessful){
                    callback(response.body()?.availableTimes)
                }else{
                    Log.e("GetAvailableTimes", "Unsuccessful response: ${response.message()}")
                    callback(null)
                }
            }
            override fun onFailure(call: Call<AvailableTimesResponse>, t: Throwable) {
                Log.e("GetAvailableTimes", "Failed to get available times", t)
                callback(null)
            }
            }
        )
    }
}