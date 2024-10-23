package pe.edu.upc.upet.feature_medicalHistory.data.remote

data class MedicalResultResponse(
    val id: Int,
    val resultDate: String,
    val resultType: String,
    val description: String,
    val medicalHistoryId: Int
)