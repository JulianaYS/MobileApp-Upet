package pe.edu.upc.upet.feature_vet.domain

typealias VetList = List<Vet>
data class Vet(
    val id: Int,
    val name: String,
    val clinicId: Int,
    val imageUrl: String,
    val description: String,
    val experience: Int,
    val userId: Int
)