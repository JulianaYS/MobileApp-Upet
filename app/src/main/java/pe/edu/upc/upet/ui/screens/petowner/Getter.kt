package pe.edu.upc.upet.ui.screens.petowner

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import pe.edu.upc.upet.feature_petOwner.data.repository.PetOwnerRepository
import pe.edu.upc.upet.feature_petOwner.domain.PetOwner
import pe.edu.upc.upet.feature_vet.data.repository.VetRepository
import pe.edu.upc.upet.feature_vet.domain.Vet
import pe.edu.upc.upet.utils.TokenManager

@Composable
fun getOwner(): PetOwner? {
    val (id, _, _) = TokenManager.getUserIdAndRoleFromToken() ?: return null

    val owner = remember {
        mutableStateOf<PetOwner?>(null)
    }

    LaunchedEffect(id) {
        PetOwnerRepository().getPetOwnerById(id) { fetchedOwner ->
            owner.value = fetchedOwner
        }
    }
    return owner.value
}

@Composable
fun getVet(): Vet? {
    val (id, _, _) = TokenManager.getUserIdAndRoleFromToken() ?: return null
    val vet = remember {
        mutableStateOf<Vet?>(null)
    }

    LaunchedEffect(id) {
        VetRepository().getVetById(id) { fetchedVet ->
            vet.value = fetchedVet
        }
    }
    return vet.value
}

fun getRole(): String? {
    val role = TokenManager.getUserIdAndRoleFromToken()?.second
    return role
}

fun isOwnerAuthenticated(): Boolean {
    val role = getRole()
    return role == "owner"
}
