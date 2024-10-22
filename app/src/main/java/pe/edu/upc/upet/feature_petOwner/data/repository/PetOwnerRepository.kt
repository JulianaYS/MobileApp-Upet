package pe.edu.upc.upet.feature_petOwner.data.repository

import android.util.Log
import pe.edu.upc.upet.feature_auth.data.remote.SignInResponse
import pe.edu.upc.upet.feature_petOwner.data.mapper.toDomainModel
import pe.edu.upc.upet.feature_petOwner.data.remote.EditPetOwnerRequest
import pe.edu.upc.upet.feature_petOwner.data.remote.PetOwnerRequest
import pe.edu.upc.upet.feature_petOwner.data.remote.PetOwnerResponse
import pe.edu.upc.upet.feature_petOwner.data.remote.PetOwnerResponseList
import pe.edu.upc.upet.feature_petOwner.data.remote.PetOwnerService
import pe.edu.upc.upet.feature_petOwner.data.remote.PetOwnerServiceFactory
import pe.edu.upc.upet.feature_petOwner.domain.PetOwner
import pe.edu.upc.upet.feature_petOwner.domain.PetOwnerList
import pe.edu.upc.upet.utils.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PetOwnerRepository(private val petOwnerService: PetOwnerService = PetOwnerServiceFactory.getPetOwnerService()) {
    fun getAllPetOwners(callback: (PetOwnerList) -> Unit) {
        petOwnerService.getAll().enqueue(object : Callback<PetOwnerResponseList> {
            override fun onResponse(
                call: Call<PetOwnerResponseList>,
                response: Response<PetOwnerResponseList>
            ) {
                if (response.isSuccessful) {
                    val petOwners = response.body()?.map { it.toDomainModel() } ?: emptyList()
                    callback(petOwners)
                } else {
                    // Handle error response
                    Log.e("PetOwnerRepository", "Failed to get pet owners: ${response.errorBody()}")
                }
            }
            override fun onFailure(call: Call<PetOwnerResponseList>, t: Throwable) {
                // Handle network error
                Log.e("PetOwnerRepository", "Failed to get pet owners", t)
            }
        })
    }
    fun getPetOwnerByUserId(userId: Int, callback: (PetOwner?) -> Unit) {
        petOwnerService.getByUserId(userId).enqueue(object : Callback<PetOwnerResponse> {
            override fun onResponse(
                call: Call<PetOwnerResponse>,
                response: Response<PetOwnerResponse>
            ) {
                if (response.isSuccessful) {
                    val petOwner = response.body()?.toDomainModel()
                    callback(petOwner)
                } else {
                    callback(null)
                }
            }
            override fun onFailure(call: Call<PetOwnerResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun updatePetOwner(petOwnerId: Int, petOwnerData: EditPetOwnerRequest, callback: (Boolean) -> Unit)
    {
        petOwnerService.updatePetOwner(petOwnerId, petOwnerData).enqueue(object :
            Callback<PetOwnerResponse> {
            override fun onResponse(
                call: Call<PetOwnerResponse>,
                response: Response<PetOwnerResponse>
            ) {
                callback(response.isSuccessful)
            }
            override fun onFailure(call: Call<PetOwnerResponse>, t: Throwable) {
                callback(false)
            }
        })
    }

    fun getPetOwnerById(id: Int, callback: (PetOwner?) -> Unit) {
        petOwnerService.getById(id).enqueue(object : Callback<PetOwnerResponse> {
            override fun onResponse(
                call: Call<PetOwnerResponse>,
                response: Response<PetOwnerResponse>
            ) {
                if (response.isSuccessful) {
                    val petOwner = response.body()?.toDomainModel()
                    callback(petOwner)
                } else {
                    callback(null)
                }
            }
            override fun onFailure(call: Call<PetOwnerResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun createPetOwner(userId: Int, petOwnerData: PetOwnerRequest, callback: (Boolean) -> Unit) {
        petOwnerService.createPetOwner(userId, petOwnerData).enqueue(object :
            Callback<SignInResponse> {
            override fun onResponse(
                call: Call<SignInResponse>,
                response: Response<SignInResponse>
            ) {
                TokenManager.clearToken()
                if (response.isSuccessful) {
                    val token = response.body()?.access_token
                    if (token != null) {
                        TokenManager.saveToken(token)
                    }
                    callback(response.isSuccessful)
                } else {
                    callback(false)
                }
            }
            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                callback(false)
            }
        })
    }
}