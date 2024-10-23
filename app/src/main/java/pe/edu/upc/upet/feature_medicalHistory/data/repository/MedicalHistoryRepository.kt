package pe.edu.upc.upet.feature_medicalHistory.data.repository

import pe.edu.upc.upet.feature_medicalHistory.data.remote.DiseaseRequest
import pe.edu.upc.upet.feature_medicalHistory.data.remote.DiseaseResponse
import pe.edu.upc.upet.feature_medicalHistory.data.remote.MedicalHistoryRequest
import pe.edu.upc.upet.feature_medicalHistory.data.remote.MedicalHistoryResponse
import pe.edu.upc.upet.feature_medicalHistory.data.remote.MedicalHistoryService
import pe.edu.upc.upet.feature_medicalHistory.data.remote.MedicalHistoryServiceFactory
import pe.edu.upc.upet.feature_medicalHistory.data.remote.MedicalResultRequest
import pe.edu.upc.upet.feature_medicalHistory.data.remote.MedicalResultResponse
import pe.edu.upc.upet.feature_medicalHistory.data.remote.SurgeryRequest
import pe.edu.upc.upet.feature_medicalHistory.data.remote.SurgeryResponse
import pe.edu.upc.upet.feature_medicalHistory.data.remote.VaccineRequest
import pe.edu.upc.upet.feature_medicalHistory.data.remote.VaccineResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MedicalHistoryRepository(
    private val medicalHistoryService: MedicalHistoryService = MedicalHistoryServiceFactory.getMedicalHistoryService()
) {
    fun getAllMedicalHistories(callback: (List<MedicalHistoryResponse>?) -> Unit) {
        medicalHistoryService.getAllMedicalHistories().enqueue(object :
            Callback<List<MedicalHistoryResponse>> {
            override fun onResponse(
                call: Call<List<MedicalHistoryResponse>>,
                response: Response<List<MedicalHistoryResponse>>
            ) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }
            override fun onFailure(call: Call<List<MedicalHistoryResponse>>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun createMedicalHistory(medicalHistory: MedicalHistoryRequest, callback: (Boolean) -> Unit) {
        medicalHistoryService.createMedicalHistory(medicalHistory).enqueue(object :
            Callback<MedicalHistoryResponse> {
            override fun onResponse(
                call: Call<MedicalHistoryResponse>,
                response: Response<MedicalHistoryResponse>
            ) {
                if (response.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                }
            }
            override fun onFailure(call: Call<MedicalHistoryResponse>, t: Throwable) {
                callback(false)
            }
        })
    }

    fun getMedicalHistoryByPetId(petId: Int, callback: (MedicalHistoryResponse?) -> Unit) {
        medicalHistoryService.getMedicalHistoryByPetId(petId).enqueue(object :
            Callback<MedicalHistoryResponse> {
            override fun onResponse(
                call: Call<MedicalHistoryResponse>,
                response: Response<MedicalHistoryResponse>
            ) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }
            override fun onFailure(call: Call<MedicalHistoryResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun getMedicalHistory(medicalHistoryId: Int, callback: (MedicalHistoryResponse?) -> Unit) {
        medicalHistoryService.getMedicalHistory(medicalHistoryId).enqueue(object :
            Callback<MedicalHistoryResponse> {
            override fun onResponse(
                call: Call<MedicalHistoryResponse>,
                response: Response<MedicalHistoryResponse>
            ) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }
            override fun onFailure(call: Call<MedicalHistoryResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun addMedicalResultToMedicalHistory(
        medicalHistoryId: Int,
        medicalResult: MedicalResultRequest,
        callback: (Boolean) -> Unit
    ) {
        medicalHistoryService.addMedicalResultToMedicalHistory(medicalHistoryId, medicalResult)
            .enqueue(object :
                Callback<MedicalResultResponse> {
                override fun onResponse(
                    call: Call<MedicalResultResponse>,
                    response: Response<MedicalResultResponse>
                ) {
                    if (response.isSuccessful) {
                        callback(true)
                    } else {
                        callback(false)
                    }
                }
                override fun onFailure(call: Call<MedicalResultResponse>, t: Throwable) {
                    callback(false)
                }
            })
    }

    fun getAllMedicalResultsByMedicalHistoryId(
        medicalHistoryId: Int,
        callback: (List<MedicalResultResponse>?) -> Unit
    ) {
        medicalHistoryService.getAllMedicalResultsByMedicalHistoryId(medicalHistoryId)
            .enqueue(object :
                Callback<List<MedicalResultResponse>> {
                override fun onResponse(
                    call: Call<List<MedicalResultResponse>>,
                    response: Response<List<MedicalResultResponse>>
                ) {
                    if (response.isSuccessful) {
                        callback(response.body())
                    } else {
                        callback(null)
                    }
                }
                override fun onFailure(call: Call<List<MedicalResultResponse>>, t: Throwable) {
                    callback(null)
                }
            })
    }

    fun addDiseaseToMedicalHistory(
        medicalHistoryId: Int,
        disease: DiseaseRequest,
        callback: (Boolean) -> Unit
    ) {
        medicalHistoryService.addDiseaseToMedicalHistory(medicalHistoryId, disease).enqueue(object :
            Callback<DiseaseResponse> {
            override fun onResponse(
                call: Call<DiseaseResponse>,
                response: Response<DiseaseResponse>
            ) {
                if (response.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                }
            }
            override fun onFailure(call: Call<DiseaseResponse>, t: Throwable) {
                callback(false)
            }
        })
    }

    fun getAllDiseasesByMedicalHistoryId(
        medicalHistoryId: Int,
        callback: (List<DiseaseResponse>?) -> Unit
    ) {
        medicalHistoryService.getAllDiseasesByMedicalHistoryId(medicalHistoryId).enqueue(object :
            Callback<List<DiseaseResponse>> {
            override fun onResponse(
                call: Call<List<DiseaseResponse>>,
                response: Response<List<DiseaseResponse>>
            ) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }
            override fun onFailure(call: Call<List<DiseaseResponse>>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun addSurgeryToMedicalHistory(
        medicalHistoryId: Int,
        surgery: SurgeryRequest,
        callback: (Boolean) -> Unit
    ) {
        medicalHistoryService.addSurgeryToMedicalHistory(medicalHistoryId, surgery).enqueue(object :
            Callback<SurgeryResponse> {
            override fun onResponse(
                call: Call<SurgeryResponse>,
                response: Response<SurgeryResponse>
            ) {
                if (response.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                }
            }
            override fun onFailure(call: Call<SurgeryResponse>, t: Throwable) {
                callback(false)
            }
        })
    }

    fun getAllSurgeriesByMedicalHistoryId(
        medicalHistoryId: Int,
        callback: (List<SurgeryResponse>?) -> Unit
    ) {
        medicalHistoryService.getAllSurgeriesByMedicalHistoryId(medicalHistoryId).enqueue(object :
            Callback<List<SurgeryResponse>> {
            override fun onResponse(
                call: Call<List<SurgeryResponse>>,
                response: Response<List<SurgeryResponse>>
            ) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }
            override fun onFailure(call: Call<List<SurgeryResponse>>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun addVaccineToMedicalHistory(
        medicalHistoryId: Int,
        vaccine: VaccineRequest,
        callback: (Boolean) -> Unit
    ) {
        medicalHistoryService.addVaccineToMedicalHistory(medicalHistoryId, vaccine).enqueue(object :
            Callback<VaccineResponse> {
            override fun onResponse(
                call: Call<VaccineResponse>,
                response: Response<VaccineResponse>
            ) {
                if (response.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                }
            }
            override fun onFailure(call: Call<VaccineResponse>, t: Throwable) {
                callback(false)
            }
        })
    }

    fun getAllVaccinesByMedicalHistoryId(
        medicalHistoryId: Int,
        callback: (List<VaccineResponse>?) -> Unit
    ) {
        medicalHistoryService.getAllVaccinesByMedicalHistoryId(medicalHistoryId).enqueue(object :
            Callback<List<VaccineResponse>> {
            override fun onResponse(
                call: Call<List<VaccineResponse>>,
                response: Response<List<VaccineResponse>>
            ) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }
            override fun onFailure(call: Call<List<VaccineResponse>>, t: Throwable) {
                callback(null)
            }
        })
    }
}