package pe.edu.upc.upet.feature_petOwner.domain

import pe.edu.upc.upet.feature_petOwner.data.remote.SubscriptionType

typealias PetOwnerList = List<PetOwner>
data class PetOwner(
    val id: Int,
    val name: String,
    val numberPhone: String,
    val location: String,
    var imageUrl: String,
    val subscriptionType: SubscriptionType
)
