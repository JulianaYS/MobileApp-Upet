package pe.edu.upc.upet.feature_medicalHistory.data.remote

data class MedicalHistoryResponse(
    val id: Int,
    val petId: Int,
    val date: String,
    val description: String
)