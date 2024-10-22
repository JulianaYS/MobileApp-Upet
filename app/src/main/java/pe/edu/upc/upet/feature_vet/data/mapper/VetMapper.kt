package pe.edu.upc.upet.feature_vet.data.mapper

import pe.edu.upc.upet.feature_vet.data.remote.VetResponse
import pe.edu.upc.upet.feature_vet.domain.Vet

fun VetResponse.toDomainModel(): Vet {
    return Vet(
        id = this.id,
        name = this.name,
        clinicId = this.clinicId,
        imageUrl = this.imageUrl,
        description = this.description?:"",
        experience = this.experience?:0,
        userId = this.userId
    )
}
