package pe.edu.upc.upet.feature_auth.data.remote

import pe.edu.upc.upet.core_network.RetrofitFactory

class AuthServiceFactory private constructor() {

    companion object {
        fun getAuthService(): AuthService {
            return RetrofitFactory.getRetrofit().create(AuthService::class.java)
        }

    }
}