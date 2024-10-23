package pe.edu.upc.upet.feature_medicalHistory.data.remote

data class MedicalHistoryRequest(
    val petId: Int,
    val date: String,
    val description: String
)