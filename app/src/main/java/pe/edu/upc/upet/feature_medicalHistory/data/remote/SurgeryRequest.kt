package pe.edu.upc.upet.feature_medicalHistory.data.remote

data class SurgeryRequest(
    val surgeryDate: String,
    val description: String,
    val medicalHistoryId: Int
)