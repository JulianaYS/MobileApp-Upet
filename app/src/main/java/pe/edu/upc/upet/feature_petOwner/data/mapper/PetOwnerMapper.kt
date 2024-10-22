package pe.edu.upc.upet.feature_petOwner.data.mapper

import pe.edu.upc.upet.feature_petOwner.data.remote.PetOwnerResponse
import pe.edu.upc.upet.feature_petOwner.domain.PetOwner

fun PetOwnerResponse.toDomainModel(): PetOwner {
    return PetOwner(
        id = this.id,
        name = this.name,
        numberPhone = this.numberPhone,
        location = this.location,
        imageUrl = this.image_url,
        subscriptionType = this.subscriptionType
    )
}
