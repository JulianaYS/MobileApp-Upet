package pe.edu.upc.upet.feature_auth.data.remote

class UserRequest (
    val name: String,
    val email: String,
    val password: String,
    val userType: UserType
)

enum class UserType {
    Vet,
    Owner
}