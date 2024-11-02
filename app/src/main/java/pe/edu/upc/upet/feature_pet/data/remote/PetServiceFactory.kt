package pe.edu.upc.upet.feature_pet.data.remote

import pe.edu.upc.upet.core_network.RetrofitFactory

class PetServiceFactory private constructor() {
    companion object {
        fun getPetService(): PetService {
            return RetrofitFactory.getRetrofit().create(PetService::class.java)
        }
    }
}