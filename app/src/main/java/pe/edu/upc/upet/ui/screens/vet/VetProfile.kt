package pe.edu.upc.upet.ui.screens.vet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person4
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pe.edu.upc.upet.feature_vet.domain.Vet
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.screens.petowner.getVet
import pe.edu.upc.upet.ui.screens.petowner.profile.InfoRowData
import pe.edu.upc.upet.ui.screens.petowner.profile.ProfileButtons
import pe.edu.upc.upet.ui.screens.petowner.profile.ProfileHeader
import pe.edu.upc.upet.ui.screens.petowner.profile.UserInfo
import pe.edu.upc.upet.ui.shared.TopBar
import pe.edu.upc.upet.utils.TokenManager

@Composable
fun VetProfile(navController: NavHostController) {
    val vet = getVet() ?: return
    val userEmail = TokenManager.getEmail() ?: return

    Scaffold(
        topBar = { TopBar(navController = navController, title = "My Profile") },

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            ProfileHeader(vet.id, vet.imageUrl)
            ProfileContent(navController, vet, userEmail)

            ProfileButtons(
                editProfile = { navController.navigate(Routes.VetEditProfile.route) },
                logout = {
                    TokenManager.clearToken()
                    navController.navigate(Routes.SignIn.route)
                },
                generatePassword = {
                    navController.navigate(Routes.GeneratePassword.createRoute(vet.clinicId))
                }
            )
        }
    }
}


@Composable
fun ProfileContent(navController: NavHostController, vet: Vet, userEmail: String) {
    //val clinic = remember { mutableStateOf<VeterinaryClinic?>(null) }

    UserInfo(name = "Vet : " + vet.name, email = userEmail, infoRows =
    listOf(
        InfoRowData(Icons.Default.Person4, "Description ", vet.description),
        InfoRowData(Icons.Default.Home, "Experience ", vet.experience.toString()+" years"),
    )
    )

    LaunchedEffect(key1 = vet.clinicId) {
        //VeterinaryClinicRepository().getVeterinaryClinicById(vet.clinicId) {
          //  clinic.value = it
        //}
    }

    //clinic.value?.let {
        //VetClinicCard(navController, it)
    //}
}