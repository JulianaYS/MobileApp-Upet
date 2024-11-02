package pe.edu.upc.upet.feature_medicalHistory.data.remote

import pe.edu.upc.upet.core_network.RetrofitFactory

class MedicalHistoryServiceFactory private constructor(){
    companion object {
        fun getMedicalHistoryService(): MedicalHistoryService {
            return RetrofitFactory.getRetrofit().create(MedicalHistoryService::class.java)
        }
    }
}
