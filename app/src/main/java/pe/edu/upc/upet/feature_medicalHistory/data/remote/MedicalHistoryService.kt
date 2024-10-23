package pe.edu.upc.upet.feature_medicalHistory.data.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MedicalHistoryService {
    @GET("medicalhistories")
    fun getAllMedicalHistories(): Call<List<MedicalHistoryResponse>>

    @POST("medicalhistories")
    fun createMedicalHistory(@Body medicalHistoryRequest: MedicalHistoryRequest): Call<MedicalHistoryResponse>

    @GET("medicalhistories/pet/{pet_id}")
    fun getMedicalHistoryByPetId(@Path("pet_id") petId: Int): Call<MedicalHistoryResponse>

    @GET("medicalhistories/{medical_history_id}")
    fun getMedicalHistory(@Path("medical_history_id") medicalHistoryId: Int): Call<MedicalHistoryResponse>

    @POST("medicalhistories/{medical_history_id}/medicalresults")
    fun addMedicalResultToMedicalHistory(@Path("medical_history_id") medicalHistoryId: Int, @Body medicalResultRequest: MedicalResultRequest): Call<MedicalResultResponse>

    @GET("medicalhistories/{medical_history_id}/medicalresults")
    fun getAllMedicalResultsByMedicalHistoryId(@Path("medical_history_id") medicalHistoryId: Int): Call<List<MedicalResultResponse>>

    @POST("medicalhistories/{medical_history_id}/diseases")
    fun addDiseaseToMedicalHistory(@Path("medical_history_id") medicalHistoryId: Int, @Body diseaseRequest: DiseaseRequest): Call<DiseaseResponse>

    @GET("medicalhistories/{medical_history_id}/diseases")
    fun getAllDiseasesByMedicalHistoryId(@Path("medical_history_id") medicalHistoryId: Int): Call<List<DiseaseResponse>>

    @POST("medicalhistories/{medical_history_id}/surgeries")
    fun addSurgeryToMedicalHistory(@Path("medical_history_id") medicalHistoryId: Int, @Body surgeryRequest: SurgeryRequest): Call<SurgeryResponse>

    @GET("medicalhistories/{medical_history_id}/surgeries")
    fun getAllSurgeriesByMedicalHistoryId(@Path("medical_history_id") medicalHistoryId: Int): Call<List<SurgeryResponse>>

    @POST("medicalhistories/{medical_history_id}/vaccines")
    fun addVaccineToMedicalHistory(@Path("medical_history_id") medicalHistoryId: Int, @Body vaccineRequest: VaccineRequest): Call<VaccineResponse>

    @GET("medicalhistories/{medical_history_id}/vaccines")
    fun getAllVaccinesByMedicalHistoryId(@Path("medical_history_id") medicalHistoryId: Int): Call<List<VaccineResponse>>
}