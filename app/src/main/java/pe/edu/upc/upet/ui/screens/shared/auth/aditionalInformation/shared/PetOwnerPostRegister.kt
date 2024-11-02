package pe.edu.upc.upet.ui.screens.shared.auth.aditionalInformation.shared

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import pe.edu.upc.upet.feature_petOwner.data.remote.PetOwnerRequest
import pe.edu.upc.upet.feature_petOwner.data.repository.PetOwnerRepository
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.shared.AuthButton
import pe.edu.upc.upet.ui.shared.AuthInputTextField
import pe.edu.upc.upet.ui.shared.TextFieldType

@Composable
fun PetOwnerPostRegister(navigateTo: (String) -> Unit={}, userId : Int){
    PostRegister(
        description = { PostRegisterDescription(
            title = "Aditional Information",
            description = "Add location and phone number for enhanced pet care services."
        ) },
        form = { PetOwnerPostRegisterForm(navigateTo, userId) }
    )

}
@Composable
fun PetOwnerPostRegisterForm(navigateTo: (String) -> Unit, userId: Int){
    val numberPhone = remember { mutableStateOf("") }
    val location = remember { mutableStateOf("") }
    val showDialog = remember { mutableStateOf(false) }
    val dialogMessage = remember { mutableStateOf("") }
    val context = LocalContext.current

    Column{
        AuthInputTextField(
            input = numberPhone,
            placeholder = "Enter your phone number",
            label = "Phone",
            type = TextFieldType.Phone
        )
        Spacer(modifier = Modifier.height(22.dp))
        AuthInputTextField(
            input = location,
            placeholder = "Av. Primavera 1262, Lima 15023",
            label = "Location",
            type = TextFieldType.Text
        )
        Spacer(modifier = Modifier.height(22.dp))

        Spacer(modifier = Modifier.height(22.dp))
        AuthButton(text = "Send",
            onClick = {

                val coordinates = getCoordinatesFromAddress(context, location.value)
                dialogMessage.value = "Coordinates: $coordinates"
                showDialog.value = true
            }
        )
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = "Confirm Coordinates") },
            text = { Text(text = dialogMessage.value) },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                        createNewPetOwner(userId, PetOwnerRequest(
                            numberPhone = numberPhone.value,
                            location = dialogMessage.value.split(":")[1].trim() // Extract coordinates from message
                        ), navigateTo = { navigateTo(Routes.OwnerHome.route) }
                        )
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog.value = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

// This function is used to get the coordinates from an address using the Geocoder class
// It receives a context and an address as parameters
// It returns a concatenated string with the coordinates of the address
// That string is sent to the createNewPetOwner function
fun getCoordinatesFromAddress(context: Context, mAddress: String): String {
    val coder = Geocoder(context)
    lateinit var address: List<Address>
    return try {
        address = coder.getFromLocationName(mAddress, 1)!!
        val location = address[0]
        "${location.latitude},${location.longitude}"
    } catch (e: Exception) {
        "Fail to find Lat,Lng"
    }
}

fun createNewPetOwner(userId: Int,
                      petOwnerData: PetOwnerRequest,
                      petOwnerRepository: PetOwnerRepository = PetOwnerRepository(),
                      navigateTo: () -> Unit
){
    petOwnerRepository.createPetOwner(userId, petOwnerData) {
        navigateTo()
        println("Pet Owner created")
    }
}
