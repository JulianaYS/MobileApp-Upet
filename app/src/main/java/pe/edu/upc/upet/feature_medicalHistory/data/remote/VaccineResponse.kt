package pe.edu.upc.upet.feature_medicalHistory.data.remote

data class VaccineResponse(
    val id: Int,
    val name: String,
    val vaccineDate: String,
    val vaccineType: String,
    val dose: String,
    val location: String,
    val medicalHistoryId: Int
)