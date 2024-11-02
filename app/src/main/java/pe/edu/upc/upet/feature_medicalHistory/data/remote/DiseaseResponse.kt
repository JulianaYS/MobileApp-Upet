package pe.edu.upc.upet.feature_medicalHistory.data.remote

data class DiseaseResponse(
    val id: Int,
    val name: String,
    val medicalHistoryId: Int,
    val diagnosisDate: String,
    val severity: String
)