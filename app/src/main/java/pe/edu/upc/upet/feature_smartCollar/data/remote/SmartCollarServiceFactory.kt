package pe.edu.upc.upet.feature_smartCollar.data.remote

import pe.edu.upc.upet.core_network.RetrofitFactory

class SmartCollarServiceFactory private constructor() {
    companion object {
        fun getSmartCollarService(): SmartCollarService {
            return RetrofitFactory.getRetrofit().create(SmartCollarService::class.java)
        }
    }
}