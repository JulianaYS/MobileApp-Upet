package pe.edu.upc.upet.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import pe.edu.upc.upet.ui.screens.petowner.OwnerHome
import pe.edu.upc.upet.ui.screens.petowner.profile.OwnerEditProfile
import pe.edu.upc.upet.ui.screens.petowner.profile.OwnerProfile
import pe.edu.upc.upet.ui.screens.shared.auth.aditionalInformation.PostRegisterScreen
import pe.edu.upc.upet.ui.screens.shared.auth.recovery.ConfirmCodeScreen
import pe.edu.upc.upet.ui.screens.shared.auth.recovery.NewPasswordScreen
import pe.edu.upc.upet.ui.screens.shared.auth.recovery.SendEmailScreen
import pe.edu.upc.upet.ui.screens.shared.auth.signin.SignInScreen
import pe.edu.upc.upet.ui.screens.shared.auth.signup.SignUpScreen
import pe.edu.upc.upet.ui.screens.vet.VetHome
import pe.edu.upc.upet.ui.screens.vet.VetProfile
import pe.edu.upc.upet.ui.theme.Pink
import pe.edu.upc.upet.utils.TokenManager

data class BottomNavItem(val name: String, val route: String, val icon: ImageVector)

val ownerBottomNavItems = listOf(
    BottomNavItem("Home", Routes.OwnerHome.route, Icons.Default.Home),
    BottomNavItem("Pets", Routes.PetList.route, Icons.Default.Pets),
    BottomNavItem("Clinics", Routes.OwnerClinicList.route, Icons.Default.LocalHospital),
    BottomNavItem("Appointments", Routes.AppointmentList.route, Icons.Default.Event),
    BottomNavItem("Profile", Routes.OwnerProfile.route, Icons.Default.Person)
)

val vetBottomNavItems = listOf(
    BottomNavItem("Home", Routes.VetHome.route, Icons.Default.Home),
    BottomNavItem("Appointments", Routes.VetAppointments.route, Icons.Default.Event),
    BottomNavItem("Reviews", Routes.VetPatients.route, Icons.Default.Comment),
    BottomNavItem("Profile", Routes.VetProfile.route, Icons.Default.Person),
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val backgroundColor = Color(0xFF0B1C3F)
    val shouldShowBottomBar = remember { mutableStateOf(true) }
    val role = remember { mutableStateOf("") }

    val bottomNavItems = when (role.value) {
        "Owner" -> ownerBottomNavItems
        "Vet" -> vetBottomNavItems
        else -> emptyList()
    }
    Log.d("Navigation", "Role: $role")

    Scaffold(
        modifier = Modifier.background(backgroundColor),
        bottomBar = {
            if (shouldShowBottomBar.value) {
                NavigationBar(containerColor = Color.White) {
                    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            selected = currentDestination == item.route,
                            onClick = { navController.navigate(item.route) },
                            icon = { Icon(item.icon, contentDescription = item.name) },
                            label = { Text(item.name) },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Pink,
                                unselectedTextColor = Color.Gray, selectedTextColor = Pink,
                            )
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController,
            startDestination = Routes.SignIn.route,
            modifier = Modifier.padding(paddingValues)
        ) {

            // Auth ---------------------------------------------------------
            composable(Routes.SignIn.route) {
                shouldShowBottomBar.value = false
                SignInScreen { navController.navigate(it) }
            }
            composable(Routes.SignUp.route) {
                shouldShowBottomBar.value = false
                SignUpScreen { navController.navigate(it) }
            }
            composable(Routes.PostRegister.route) {
                shouldShowBottomBar.value = false
                PostRegisterScreen { navController.navigate(it) }
            }
            composable(Routes.ConfirmCode.route) {
                shouldShowBottomBar.value = false
                ConfirmCodeScreen(navController)
            }
            composable(Routes.NewPassword.route) {
                shouldShowBottomBar.value = false
                NewPasswordScreen(navController)
            }
            composable(Routes.SendEmail.route) {
                shouldShowBottomBar.value = false
                SendEmailScreen(navController)
            }


            // Owner routes -----------------------------------------------

            composable(Routes.OwnerHome.route) {
                role.value = TokenManager.getUserIdAndRoleFromToken()?.second ?: ""
                shouldShowBottomBar.value = true
                OwnerHome(navController)
            }

            composable(Routes.OwnerProfile.route) {
                shouldShowBottomBar.value = true
                OwnerProfile(navController)
            }
            composable(Routes.OwnerEditProfile.route) {
                shouldShowBottomBar.value = true
                OwnerEditProfile(navController)
            }


            // Vet routes ---------------------------------------------------------------------------------
            composable(Routes.VetHome.route) {
                role.value = TokenManager.getUserIdAndRoleFromToken()?.second ?: ""

                shouldShowBottomBar.value = true
                VetHome(navController)
            }
            composable(Routes.VetProfile.route) {
                shouldShowBottomBar.value = true
                VetProfile(navController)
            }

        }

    }
}