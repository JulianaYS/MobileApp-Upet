package pe.edu.upc.upet.ui.screens.petowner.pet

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.feature_pet.data.remote.GenderEnum
import pe.edu.upc.upet.feature_pet.data.remote.PetRequest
import pe.edu.upc.upet.feature_pet.data.repository.PetRepository
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.shared.AuthButton
import pe.edu.upc.upet.ui.shared.AuthInputTextField
import pe.edu.upc.upet.ui.shared.CustomReturnButton
import pe.edu.upc.upet.ui.shared.Dialog
import pe.edu.upc.upet.ui.shared.InputDate
import pe.edu.upc.upet.ui.shared.InputDropdownField
import pe.edu.upc.upet.ui.shared.LabelTextField
import pe.edu.upc.upet.ui.shared.RadioButtonsOptions
import pe.edu.upc.upet.ui.shared.SuccessDialog
import pe.edu.upc.upet.ui.shared.TextFieldType
import pe.edu.upc.upet.ui.shared.TopBar
import pe.edu.upc.upet.ui.shared.uploadImage
import pe.edu.upc.upet.ui.theme.BorderPadding
import pe.edu.upc.upet.ui.theme.Pink
import pe.edu.upc.upet.ui.theme.UpetBackGroundPrimary
import pe.edu.upc.upet.ui.theme.poppinsFamily
import pe.edu.upc.upet.utils.TokenManager
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterPet(navController: NavHostController) {
    val (id, _, _) = TokenManager.getUserIdAndRoleFromToken() ?: error("Error obteniendo el userId y userRole desde el token")
    val showSuccessDialog = remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier,
        topBar = {
            TopBar(navController = navController, title = "Pet Form")
        }
    ) { paddingValues ->
        val name = remember { mutableStateOf("") }
        val breed = remember { mutableStateOf("") }
        val weight = remember { mutableStateOf("") }
        val imageUrl = remember { mutableStateOf<Uri?>(null) }
        val selectedDate = remember { mutableStateOf("") }
        val selectedGender = remember { mutableIntStateOf(0) }
        val showErrorSnackbar = remember { mutableStateOf(false) }
        val snackbarMessage = remember { mutableStateOf("") }


        val typeOptions =
            listOf("Dog", "Cat", "Bird", "Fish", "Reptile", "Rodent", "Rabbit", "Other")
        val selectedType = remember { mutableStateOf(typeOptions[0]) }

        val pickImage =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
                uri?.let {
                    imageUrl.value = uri
                }
            }
        Box(modifier = Modifier.fillMaxSize()
            .background(Color(0xFFF0F6FF))
            .padding(top = BorderPadding, bottom = BorderPadding)
        ) {
            LazyColumn {
                item {
                    Column(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                            ,
                        verticalArrangement = Arrangement.spacedBy(13.dp)
                    ) {

                        PetImageRegister(
                            text = "Upload image",
                            onPickImageClick = { pickImage.launch("image/*") },
                            imageUrl = imageUrl.value.toString()
                        )

                        AuthInputTextField(
                            input = name,
                            placeholder = "Enter your pet's name",
                            label = "Pet Name"
                        )

                        InputDate(text = "Date of birth", onDateSelected = { date ->
                            selectedDate.value = date
                        })

                        InputDropdownField(
                            label = "Type of Animal",
                            options = typeOptions,
                            selectedOption = selectedType,
                        )
                        AuthInputTextField(
                            input = breed,
                            placeholder = "Enter your pet's breed",
                            label = "Breed"
                        )
                        AuthInputTextField(
                            input = weight,
                            placeholder = "Enter your pet's weight in Kg",
                            label = "Weight (Kg)",
                            type = TextFieldType.Phone
                        )
                        GenderOption(selectedGender)
                        AuthButton(text = "Save", onClick = {
                            if (name.value.isEmpty()) {
                                snackbarMessage.value = "You must enter your pet's name."
                                showErrorSnackbar.value = true
                            } else if (selectedDate.value.isEmpty()) {
                                snackbarMessage.value = "You must enter a pet's date of birth."
                                showErrorSnackbar.value = true
                            } else if (selectedType.value.isEmpty()) {
                                snackbarMessage.value = "You must enter your pet's type."
                                showErrorSnackbar.value = true
                            } else if (breed.value.isEmpty()) {
                                snackbarMessage.value = "You must enter your pet's breed."
                                showErrorSnackbar.value = true
                            } else if (weight.value.isEmpty()) {
                                snackbarMessage.value = "You must enter your pet's weight."
                                showErrorSnackbar.value = true
                            } else if (imageUrl.value == null) {
                                snackbarMessage.value = "You must upload an image."
                                showErrorSnackbar.value = true
                            } else {

                                uploadImage(imageUrl.value!!) { url ->
                                    if (url == "") {
                                        snackbarMessage.value = "Failed to upload image. "
                                        showErrorSnackbar.value = true
                                    } else {
                                        val currentFormatter =
                                            DateTimeFormatter.ofPattern("d/M/yyyy")
                                        val requiredFormatter =
                                            DateTimeFormatter.ofPattern("yyyy-MM-dd")
                                        val date =
                                            LocalDate.parse(selectedDate.value, currentFormatter)
                                        val formattedDate = date.format(requiredFormatter)

                                        PetRepository().createPet(
                                            id,
                                            PetRequest(
                                                name = name.value,
                                                breed = breed.value,
                                                species = selectedType.value,
                                                weight = weight.value.toFloat(),
                                                birthdate = formattedDate,
                                                image_url = url,
                                                gender = if (selectedGender.intValue == 0) GenderEnum.Male.toString() else GenderEnum.Female.toString()
                                            )
                                        ) { success ->
                                            if (success) {

                                                /*val medicalHistory = MedicalHistoryRequest(
                                                    petId = 1,
                                                    date = formattedDate,
                                                    description = "Pet medical history for ${name.value} - ${breed.value} - ${selectedType.value}."
                                                )
                                                MedicalHistoryRepository().createMedicalHistory(medicalHistory) {
                                                }*/
                                                showSuccessDialog.value = true
                                            } else {
                                                snackbarMessage.value = "Failed to register pet."
                                                showErrorSnackbar.value = true
                                            }

                                        }
                                    }

                                }
                            }
                        })
                    }
                }

            }
            Dialog(message = snackbarMessage.value, showError = showErrorSnackbar)
        }

        if (showSuccessDialog.value) {

            SuccessDialog(
                onDismissRequest = {
                    showSuccessDialog.value = false
                    navController.navigate(Routes.OwnerHome.route)
                }, titleText = "Pet Registered",
                messageText = "Your pet has been registered successfully.",
                buttonText = "OK"
            )

        }
    }
}




@Composable
fun GenderOption( selectedOption: MutableState<Int> = mutableIntStateOf(1)){
    Column (
        modifier = Modifier
    ){
        Text(text ="Gender", style = TextStyle(
            color = Color.White,
            fontSize = 12.sp,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Medium
        )
        )
        RadioButtonsOptions(
            option1 = "Male",
            option2 = "Female",
            selectedOption = selectedOption
        )
    }
}

@Composable
fun PetImageRegister(text: String, onPickImageClick: () -> Unit, imageUrl: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val commonPadding = BorderPadding
        LabelTextField(text, commonPadding)
        Box(
            modifier = Modifier
                .size(110.dp)
                .border(3.dp, Pink, RoundedCornerShape(4.dp))
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0x10FFFFFF))
                .align(Alignment.CenterHorizontally)
                .clickable(onClick = onPickImageClick), // Add clickable to the Box
            contentAlignment = Alignment.Center
        ) {

            IconButton(onClick = onPickImageClick) {
                Icon(Icons.Filled.Upload, contentDescription = "Upload Icon", tint = Pink)
            }

            GlideImage(
                imageModel = { imageUrl },
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
