package pe.edu.upc.upet.feature_vetClinic.data.remote

import pe.edu.upc.upet.core_network.RetrofitFactory

class VeterinaryClinicServiceFactory {
    companion object{
        fun getVeterinaryService(): VeterinaryClinicService{
            return RetrofitFactory.getRetrofit().create(VeterinaryClinicService::class.java)
        }
    }
}