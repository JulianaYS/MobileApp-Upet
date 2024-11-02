package pe.edu.upc.upet.feature_medicalHistory.data.remote

data class SurgeryResponse(
    val id: Int,
    val surgeryDate: String,
    val description: String,
    val medicalHistoryId: Int
)