package pe.edu.upc.upet.feature_vetClinic.data.remote

import com.google.gson.annotations.SerializedName

class VeterinaryClinicRequest (
    val name: String,
    val location : String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    val description: String,
    @SerializedName("office_hours_start")
    val officeHoursStart : String,
    @SerializedName("office_hours_end")
    val officeHoursEnd : String,

    )
