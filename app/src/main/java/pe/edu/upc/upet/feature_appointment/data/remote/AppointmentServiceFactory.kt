package pe.edu.upc.upet.feature_appointment.data.remote

import pe.edu.upc.upet.core_network.RetrofitFactory

class AppointmentServiceFactory private constructor(){
    companion object {
        fun getAppointmentService(): AppointmentService {
            return RetrofitFactory.getRetrofit().create(AppointmentService::class.java)
        }
    }
}