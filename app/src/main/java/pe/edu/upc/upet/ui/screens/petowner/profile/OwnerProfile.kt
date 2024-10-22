package pe.edu.upc.upet.ui.screens.petowner.profile

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person4
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import pe.edu.upc.upet.feature_petOwner.data.remote.SubscriptionType
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.screens.petowner.getOwner
import pe.edu.upc.upet.ui.shared.CustomButton
import pe.edu.upc.upet.ui.shared.ImagePicker
import pe.edu.upc.upet.ui.shared.SuccessDialog
import pe.edu.upc.upet.ui.shared.TopBar
import pe.edu.upc.upet.utils.TokenManager

@Composable
fun OwnerProfile(navController: NavHostController) {
    val petOwner = getOwner()?: return
    val userEmail = TokenManager.getEmail()?: return

    Scaffold(
        topBar = { TopBar(navController = navController, title = "My Profile") },
        modifier = Modifier.padding(16.dp)
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

            ProfileHeader(petOwner.id, petOwner.imageUrl)
            UserInfo(petOwner.name, userEmail,
                listOf(
                    InfoRowData(Icons.Default.Person4, "Phone", petOwner.numberPhone),
                    InfoRowData(Icons.Default.Home, "Address", petOwner.location)
                )
            )
            val subscriptionRoute = if (petOwner.subscriptionType == SubscriptionType.BASIC) {
                Routes.SubscriptionBasic.route
            } else {
                Routes.SubscriptionAdvanced.route
            }
            ProfileButtons(
                manageSubscription = { navController.navigate(subscriptionRoute) },
                editProfile = { navController.navigate(Routes.OwnerEditProfile.route) },
                logout = {
                    TokenManager.clearToken()
                    navController.navigate(Routes.SignIn.route)
                }
            )
        }
    }
}

@Composable
fun ProfileHeader(id: Int, image: String) {
    val imageUrl = remember { mutableStateOf(image) }
    val newImageUri = remember { mutableStateOf<Uri?>(null) }
    val showDialog = remember { mutableStateOf(false) }

    ImagePicker(id,newImageUri,imageUrl,showDialog) {
    }

    if (showDialog.value) {
        SuccessDialog(
            onDismissRequest = { showDialog.value = false },
            titleText = "Image Updated",
            messageText = "Your profile image has been updated successfully.",
            buttonText = "OK"
        )
    }
}

@Composable
fun ProfileButtons(
    manageSubscription: (() -> Unit)? = null,
    generatePassword: (() -> Unit)? = null,
    editProfile: () -> Unit,
    logout: () -> Unit
) {
    data class ProfileButton(val text: String, val icon: ImageVector, val onClick: () -> Unit)
    val profileButtons = mutableListOf(
        ProfileButton("Edit Profile", Icons.Default.Edit, editProfile),
        ProfileButton("Logout", Icons.AutoMirrored.Filled.Logout, logout)
    )

    manageSubscription?.let {
        profileButtons.add(0, ProfileButton("Manage Subscription", Icons.Default.Star, it))
    }

    generatePassword?.let {
        profileButtons.add(0, ProfileButton("Generate Password", Icons.Default.Key, it))
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        profileButtons.forEach { button ->
            CustomButton(button.text, button.icon, button.onClick)
        }
    }
}

@Composable
fun UserInfo(
    name: String,
    email: String,
    infoRows: List<InfoRowData>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = name,
                color = Color.Black,
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = email,
                color = Color.Gray,
                fontSize = 16.sp
            )
            infoRows.forEach { infoRow ->
                InfoRow(icon = infoRow.icon, label = infoRow.label, value = infoRow.value)
            }
        }
    }
}

data class InfoRowData(
    val icon: ImageVector,
    val label: String,
    val value: String?
)

@Composable
fun InfoRow(icon: ImageVector, label: String, value: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "$label Icon",
            tint = Color(0xFF3F51B5)
        )
        Text(
            text = "$label: ${value ?: ""
            }",
            color = Color.Black,
            modifier = Modifier.padding(start = 8.dp),
            fontSize = 18.sp
        )
    }
}