package pe.edu.upc.upet.feature_smartCollar.data.repository

import pe.edu.upc.upet.feature_smartCollar.data.mapper.toDomainModel
import pe.edu.upc.upet.feature_smartCollar.data.remote.SmartCollarRequest
import pe.edu.upc.upet.feature_smartCollar.data.remote.SmartCollarResponse
import pe.edu.upc.upet.feature_smartCollar.data.remote.SmartCollarService
import pe.edu.upc.upet.feature_smartCollar.data.remote.SmartCollarServiceFactory
import pe.edu.upc.upet.feature_smartCollar.domain.SmartCollar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SmartCollarRepository(private val smartCollarService: SmartCollarService = SmartCollarServiceFactory.getSmartCollarService()) {

    fun getAllSmartCollars(callback: (List<SmartCollar>) -> Unit) {
        smartCollarService.getAllSmartsCollars().enqueue(object : Callback<List<SmartCollarResponse>> {
            override fun onResponse(call: Call<List<SmartCollarResponse>>, response: Response<List<SmartCollarResponse>>) {
                if (response.isSuccessful) {
                    val smartCollars = response.body()?.map { it.toDomainModel() } ?: emptyList()
                    callback(smartCollars)
                } else {
                    callback(emptyList())
                }
            }

            override fun onFailure(call: Call<List<SmartCollarResponse>>, t: Throwable) {
                callback(emptyList())
            }
        })
    }

    fun createSmartCollar(smartCollarRequest: SmartCollarRequest, callback: (SmartCollar?) -> Unit) {
        smartCollarService.createSmartCollar(smartCollarRequest).enqueue(object : Callback<SmartCollarResponse> {
            override fun onResponse(call: Call<SmartCollarResponse>, response: Response<SmartCollarResponse>) {
                if (response.isSuccessful) {
                    val smartCollar = response.body()?.toDomainModel()
                    callback(smartCollar)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<SmartCollarResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun changePetAssociation(collarId: Int, newPetId: Int?, callback: (Boolean) -> Unit) {
        if (newPetId != null) {
            smartCollarService.changePetAssociation(collarId, newPetId).enqueue(object : Callback<SmartCollarResponse> {
                override fun onResponse(call: Call<SmartCollarResponse>, response: Response<SmartCollarResponse>) {
                    callback(response.isSuccessful)
                }

                override fun onFailure(call: Call<SmartCollarResponse>, t: Throwable) {
                    callback(false)
                }
            })
        }
    }

    fun getSmartCollarsByPetId(petId: Int, callback: (SmartCollar?) -> Unit) {
        smartCollarService.getSmartCollarsByPetId(petId).enqueue(object : Callback<List<SmartCollarResponse>> {
            override fun onResponse(call: Call<List<SmartCollarResponse>>, response: Response<List<SmartCollarResponse>>) {
                if (response.isSuccessful) {
                    val smartCollar = response.body()?.firstOrNull()?.toDomainModel()
                    callback(smartCollar)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<SmartCollarResponse>>, t: Throwable) {
                callback(null)
            }
        })
    }
}