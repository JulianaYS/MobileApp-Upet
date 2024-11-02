package pe.edu.upc.upet.ui.screens.shared.auth.aditionalInformation.shared

import android.app.TimePickerDialog
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import pe.edu.upc.upet.feature_vet.data.remote.VetRequest
import pe.edu.upc.upet.feature_vet.data.repository.VetRepository
import pe.edu.upc.upet.feature_vetClinic.data.remote.VeterinaryClinicRequest
import pe.edu.upc.upet.feature_vetClinic.data.repository.VeterinaryClinicRepository
import pe.edu.upc.upet.feature_vetClinic.domain.VeterinaryClinic
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.shared.AuthButton
import pe.edu.upc.upet.ui.shared.AuthInputTextField
import pe.edu.upc.upet.ui.shared.LabelTextField
import pe.edu.upc.upet.ui.shared.RadioButtonsOptions
import pe.edu.upc.upet.ui.shared.TextFieldType
import java.time.LocalTime
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VeterinarianPostRegister(navigateTo : (String) -> Unit, userId: Int){
    PostRegister(
        description = { PostRegisterDescription(
            title = "Select Veterinary Clinic",
            description = "Do you want to register a new veterinary clinic or join an existing clinic?"
        ) },
        form = { VeterinarianPostRegisterForm(navigateTo,userId) }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VeterinarianPostRegisterForm(navigateTo : (String) -> Unit, userId: Int){
    val  selectedOption = remember{
        mutableIntStateOf(1)
    }
    Column {
        RadioButtonsOptions(
            option1 = "New  Clinic",
            option2 = "Existing Clinic",
            selectedOption = selectedOption
        )
        Spacer(modifier = Modifier.height(22.dp))
        if(selectedOption.intValue == 1)
            NewClinicForm(navigateTo, userId)
        else
            ExistingClinicForm(navigateTo,userId)
    }

}
@Composable
fun ExistingClinicForm(navigateTo : (String) -> Unit,
                       userId: Int,
                       veterinaryClinicRepository: VeterinaryClinicRepository = VeterinaryClinicRepository()
){

    val clinics = remember {
        mutableStateOf(emptyList<VeterinaryClinic>())
    }
    veterinaryClinicRepository.getAllVeterinaryClinics { vets ->
        clinics.value = vets
    }
    val selectedClinics = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }

    AuthInputTextField(
        input = selectedClinics,
        placeholder = "Select your clinic",
        label = "Clinic",
        type = TextFieldType.Dropdown,
        dropdownList = clinics.value.map { it.name },
    )
    Spacer(modifier = Modifier.height(22.dp))
    AuthInputTextField(
        input = password,
        placeholder = "Enter the clinic password",
        label = "Password",
        type = TextFieldType.Password
    )
    Spacer(modifier = Modifier.height(22.dp))

    AuthButton(text ="Send", onClick = {
        createVeterinary(
            userId,
            VetRequest(
                selectedClinics.value,
                password.value
            ),
            navigateTo = { navigateTo(Routes.VetHome.route)}
        )
    })
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewClinicForm(navigateTo : (String) -> Unit, userId: Int) {
    val clinicName = remember { mutableStateOf("") }
    val clinicLocation = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val officeHoursStart = remember { mutableStateOf("") }
    val officeHoursEnd = remember { mutableStateOf("") }
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    val dialogMessage = remember { mutableStateOf("") }

    LazyColumn {
        item {
            AuthInputTextField(
                input = clinicName,
                placeholder = "Enter your clinic name",
                label = "Clinic Name",
            )
            Spacer(modifier = Modifier.height(22.dp))
            AuthInputTextField(
                input = clinicLocation,
                placeholder = "Av. Primavera 1262, Lima 15023",
                label = "Location",
            )

            Spacer(modifier = Modifier.height(22.dp))
            AuthInputTextField(
                input = phoneNumber,
                placeholder = "Enter your phone number",
                label = "Phone",
                type = TextFieldType.Phone
            )
            Spacer(modifier = Modifier.height(22.dp))
            AuthInputTextField( // new field
                input = description,
                placeholder = "Enter your clinic description",
                label = "Description",
            )
            Spacer(modifier = Modifier.height(22.dp))


            Row( modifier = Modifier.padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                LabelTextField(label = "Office Hours: ", commonPadding = 4.dp)
                TimePickerInput(time = officeHoursStart)
                Text(text = " - ", style = MaterialTheme.typography.bodySmall)
                TimePickerInput(time = officeHoursEnd)
            }
            Spacer(modifier = Modifier.height(22.dp))

            AuthButton(text ="Send", onClick = {

                val coordinates = getCoordinatesFromAddressClinic(context, clinicLocation.value)
                dialogMessage.value = "Coordinates: $coordinates"
                showDialog.value = true

            })
        }
    }
    if (showDialog.value){
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = "Confirm Coordinates") },
            text = { Text(text = dialogMessage.value) },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                        createNewClinic(
                            userId,
                            VeterinaryClinicRequest(
                                name = clinicName.value,
                                location = dialogMessage.value.split(":")[1].trim(),
                                phoneNumber = phoneNumber.value,
                                description = description.value,
                                officeHoursStart = officeHoursStart.value,
                                officeHoursEnd = officeHoursEnd.value,
                            ),
                            navigateTo = { navigateTo(Routes.VetHome.route) }
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
fun getCoordinatesFromAddressClinic(context: Context, mAddress: String): String {
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


fun createVeterinary(
    userId: Int,
    vetRequest: VetRequest,
    vetRepository: VetRepository = VetRepository(),
    navigateTo: () -> Unit = {}
){
    vetRepository.createVet(userId, vetRequest){
        navigateTo()
    }
}
fun createNewClinic(userId: Int,
                    clinicRequest: VeterinaryClinicRequest,
                    clinicRepository : VeterinaryClinicRepository = VeterinaryClinicRepository(),
                    navigateTo: () -> Unit
){
    Log.d("ClinicRequest", userId.toString())
    Log.d("ClinicRequest", clinicRequest.name)
    Log.d("ClinicRequest", clinicRequest.location)
    Log.d("ClinicRequest", clinicRequest.phoneNumber)

    clinicRepository.createVeterinaryClinic(clinicRequest){ clinicResponse ->
        Log.d("ClinicResponse", clinicResponse.toString())
        val clinicId= clinicResponse?.id ?: error("Error creating clinic")
        val clinicName= clinicResponse.name
        Log.d("ClinicResponse", "Clinic created with id $clinicId")

        clinicRepository.generatePassword(clinicId){password->
            Log.d("Password", "Password generated: $password")
            createVeterinary(
                userId,
                VetRequest(
                    clinicName,
                    password.toString()
                ),
                navigateTo = navigateTo
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimePickerInput(time: MutableState<String>) {
    val context = LocalContext.current
    val openDialog = remember { mutableStateOf(false) }

    Button(onClick = { openDialog.value = true }) {
        Text(text = time.value)
    }

    if (openDialog.value) {
        Dialog(onDismissRequest = { openDialog.value = false }) {
            val picker = TimePickerDialog(
                context,
                { _, hourOfDay, minute ->
                    val selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)
                    time.value = selectedTime
                    openDialog.value = false
                },
                LocalTime.now().hour,
                LocalTime.now().minute,
                false
            )
            picker.updateTime(LocalTime.now().hour, LocalTime.now().minute)
            picker.show()
        }
    }
}