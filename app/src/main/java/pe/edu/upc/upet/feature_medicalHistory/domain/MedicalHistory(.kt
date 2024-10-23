package pe.edu.upc.upet.feature_medicalHistory.domain

data class MedicalHistory(
    val id: Int,
    val petId: Int,
    val date: String,
    val description: String,
    val medicalResults: List<MedicalResult>,
    val diseases: List<Disease>,
    val surgeries: List<Surgery>,
    val vaccines: List<Vaccine>
)

data class MedicalResult(
    val id: Int,
    val resultDate: String,
    val resultType: String,
    val description: String,
    val medicalHistoryId: Int
)

data class Disease(
    val id: Int,
    val name: String,
    val medicalHistoryId: Int,
    val diagnosisDate: String,
    val severity: String
)

data class Surgery(
    val id: Int,
    val surgeryDate: String,
    val description: String,
    val medicalHistoryId: Int
)

data class Vaccine(
    val id: Int,
    val name: String,
    val vaccineDate: String,
    val vaccineType: String,
    val dose: String,
    val location: String,
    val medicalHistoryId: Int
)