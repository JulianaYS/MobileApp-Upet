package pe.edu.upc.upet.feature_petOwner.data.remote

import pe.edu.upc.upet.core_network.RetrofitFactory

class PetOwnerServiceFactory {
    companion object {
        fun getPetOwnerService(): PetOwnerService{
            return RetrofitFactory.getRetrofit().create(PetOwnerService::class.java)
        }
    }
}