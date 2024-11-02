package pe.edu.upc.upet.ui.screens.vet

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pe.edu.upc.upet.feature_medicalHistory.data.remote.DiseaseRequest
import pe.edu.upc.upet.feature_medicalHistory.data.remote.MedicalResultRequest
import pe.edu.upc.upet.feature_medicalHistory.data.remote.SurgeryRequest
import pe.edu.upc.upet.feature_medicalHistory.data.remote.VaccineRequest
import pe.edu.upc.upet.feature_medicalHistory.data.repository.MedicalHistoryRepository
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.shared.AuthInputTextField
import pe.edu.upc.upet.ui.shared.CustomButton
import pe.edu.upc.upet.ui.shared.InputDate
import pe.edu.upc.upet.ui.shared.SuccessDialog
import pe.edu.upc.upet.ui.shared.TopBar
import pe.edu.upc.upet.ui.theme.BorderPadding
import pe.edu.upc.upet.ui.theme.Pink
import pe.edu.upc.upet.ui.theme.UpetBackGroundPrimary
import pe.edu.upc.upet.ui.theme.poppinsFamily
import java.text.SimpleDateFormat
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VetMedicalHistory(navController: NavController, petId: Int) {

    var diseaseName by remember { mutableStateOf("") }
    var diseaseDiagnosisDate by remember { mutableStateOf("") }
    var diseaseSeverity by remember { mutableStateOf("") }

    var resultDate by remember { mutableStateOf("") }
    var resultType by remember { mutableStateOf("") }
    var resultDescription by remember { mutableStateOf("") }

    var surgeryDate by remember { mutableStateOf("") }
    var surgeryDescription by remember { mutableStateOf("") }

    var vaccineName by remember { mutableStateOf("") }
    var vaccineDate by remember { mutableStateOf("") }
    var vaccineType by remember { mutableStateOf("") }
    var vaccineDose by remember { mutableStateOf("") }
    var vaccineLocation by remember { mutableStateOf("") }

    var isDiseaseExpanded by remember { mutableStateOf(false) }
    var isResultExpanded by remember { mutableStateOf(false) }
    var isSurgeryExpanded by remember { mutableStateOf(false) }
    var isVaccineExpanded by remember { mutableStateOf(false) }

    val commonDiseaseNames = listOf("Parvovirus", "Distemper", "Rabies")
    val commonVaccineTypes = listOf("Core", "Non-Core")
    val commonSeverities = listOf("Mild", "Moderate", "Severe")
    val showSuccessDialog = remember { mutableStateOf(false) }

    val medicalHistoryRepository = MedicalHistoryRepository()
    var medicalHistoryId by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(petId) {
        medicalHistoryRepository.getMedicalHistoryByPetId(petId) { medicalHistory ->
            medicalHistoryId = medicalHistory?.id
        }
    }

    if (showSuccessDialog.value) {
        SuccessDialog(
            onDismissRequest = {
                showSuccessDialog.value = false
                navController.navigate(Routes.OwnerHome.route)
            },
            titleText = "Pet Registered",
            messageText = "Your added to medical history successfully.",
            buttonText = "OK"
        )
    }

    Scaffold(
        topBar = {
            TopBar(navController, "Veterinary Forms")
        },

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(start = BorderPadding, end = BorderPadding)
                .verticalScroll(rememberScrollState())
        ) {

            ExpandableCard(
                title = "Disease",
                icon = Icons.Default.NoteAlt,
                expanded = isDiseaseExpanded,
                onExpandChange = { isDiseaseExpanded = it }
            ) {
                Column {
                    /*InputDropdownField(
                        value = diseaseName,
                        onValueChange = { diseaseName = it },
                        label = "Name",
                        suggestions = commonDiseaseNames,
                        modifier = Modifier.fillMaxWidth()
                    )*/
                    AuthInputTextField(input = remember {
                        mutableStateOf(diseaseName)
                    }, placeholder = "Enter name", label = "Name")

                    InputDate(text = "Diagnosis Date") {
                        diseaseDiagnosisDate = it
                    }
                    /*InputDropdownField(
                        value = diseaseSeverity,
                        onValueChange = { diseaseSeverity = it },
                        label = "Severity",
                        suggestions = commonSeverities,
                        modifier = Modifier.fillMaxWidth()
                    )*/
                    AuthInputTextField(input = remember {
                        mutableStateOf(diseaseSeverity)
                    }, placeholder = "Enter severity", label = "Severity")
                    CustomButton("Add Disease") {
                        if (medicalHistoryId != null) {
                            val diseaseRequest = DiseaseRequest(
                                name = diseaseName,
                                medicalHistoryId = medicalHistoryId!!,
                                diagnosisDate = convertDateFormat(diseaseDiagnosisDate),
                                severity = diseaseSeverity
                            )
                            medicalHistoryRepository.addDiseaseToMedicalHistory(medicalHistoryId!!, diseaseRequest) {
                                if (it) {
                                    Log.d("MedicalHistoryRepository", "Disease added successfully")
                                    showSuccessDialog.value = true
                                } else {
                                    Log.d("MedicalHistoryRepository", "Failed to add disease")
                                }
                            }
                        }
                    }
                }
            }

            ExpandableCard(
                title = "Medical Result",
                icon = Icons.Default.NoteAlt,
                expanded = isResultExpanded,
                onExpandChange = { isResultExpanded = it }
            ) {
                Column {
                    InputDate(text = "Result Date") {
                        resultDate = it
                    }
                    /*OutlinedTextField(
                        value = resultType,
                        onValueChange = { resultType = it },
                        label = { Text("Type") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                    )
                    OutlinedTextField(
                        value = resultDescription,
                        onValueChange = { resultDescription = it },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                    )*/
                    AuthInputTextField(input = remember {
                        mutableStateOf(resultType)
                    }, placeholder = "Enter type", label = "Type")

                    AuthInputTextField(input = remember {
                        mutableStateOf(resultDescription)
                    }, placeholder = "Enter description", label = "Description")

                    CustomButton("Add Result") {
                        if (medicalHistoryId != null) {
                            val medicalResultRequest = MedicalResultRequest(
                                resultDate = convertDateFormat(resultDate),
                                resultType = resultType,
                                description = resultDescription,
                                medicalHistoryId = medicalHistoryId!!
                            )
                            medicalHistoryRepository.addMedicalResultToMedicalHistory(medicalHistoryId!!, medicalResultRequest) {
                                if (it) {
                                    showSuccessDialog.value = true
                                    Log.d("MedicalHistoryRepository", "Medical result added successfully")
                                } else {
                                    Log.d("MedicalHistoryRepository", "Failed to add medical result")
                                }
                            }
                        }
                    }
                }
            }

            ExpandableCard(
                title = "Surgery",
                icon = Icons.Default.NoteAlt,
                expanded = isSurgeryExpanded,
                onExpandChange = { isSurgeryExpanded = it }
            ) {
                Column {
                    InputDate(text = "Surgery Date") {
                        surgeryDate = it
                    }
                    /*OutlinedTextField(
                        value = surgeryDescription,
                        onValueChange = { surgeryDescription = it },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                    )*/
                    AuthInputTextField(input = remember {
                        mutableStateOf(surgeryDescription)
                    }, placeholder = "Enter description", label = "Description")

                    CustomButton("Add Surgery") {
                        if (medicalHistoryId != null) {
                            val surgeryRequest = SurgeryRequest(
                                surgeryDate = convertDateFormat(surgeryDate),
                                description = surgeryDescription,
                                medicalHistoryId = medicalHistoryId!!
                            )
                            medicalHistoryRepository.addSurgeryToMedicalHistory(medicalHistoryId!!, surgeryRequest) {
                                if (it) {
                                    showSuccessDialog.value = true
                                    Log.d("MedicalHistoryRepository", "Surgery added successfully")
                                } else {
                                    Log.d("MedicalHistoryRepository", "Failed to add surgery")
                                }
                            }
                        }
                    }
                }
            }

            ExpandableCard(
                title = "Vaccine",
                icon = Icons.Default.NoteAlt,
                expanded = isVaccineExpanded,
                onExpandChange = { isVaccineExpanded = it }
            ) {
                Column {
                    /*OutlinedTextField(
                        value = vaccineName,
                        onValueChange = { vaccineName = it },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                    )*/
                    AuthInputTextField(input = remember {
                        mutableStateOf(vaccineName)
                    }, placeholder = "Enter name", label = "Name")

                    InputDate(text = "Vaccine Date") {
                        vaccineDate = it
                    }
                    /*InputDropdownField(
                        value = vaccineType,
                        onValueChange = { vaccineType = it },
                        label = "Type",
                        suggestions = commonVaccineTypes,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = vaccineDose,
                        onValueChange = { vaccineDose = it },
                        label = { Text("Dose") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                    )
                    OutlinedTextField(
                        value = vaccineLocation,
                        onValueChange = { vaccineLocation = it },
                        label = { Text("Location") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                    )*/
                    AuthInputTextField(input = remember {
                        mutableStateOf(vaccineType)
                    }, placeholder = "Enter type", label = "Type")

                    AuthInputTextField(input = remember {
                        mutableStateOf(vaccineDose)
                    }, placeholder = "Enter dose", label = "Dose")

                    AuthInputTextField(input = remember {
                        mutableStateOf(vaccineType)
                    }, placeholder = "Enter location", label = "Location")

                    CustomButton("Add Vaccine") {
                        if (medicalHistoryId != null) {
                            val vaccineRequest = VaccineRequest(
                                name = vaccineName,
                                vaccineDate = convertDateFormat(vaccineDate),
                                vaccineType = vaccineType,
                                dose = vaccineDose,
                                location = vaccineLocation,
                                medicalHistoryId = medicalHistoryId!!
                            )
                            medicalHistoryRepository.addVaccineToMedicalHistory(medicalHistoryId!!, vaccineRequest) {
                                if (it) {
                                    showSuccessDialog.value = true
                                    Log.d("MedicalHistoryRepository", "Vaccine added successfully")
                                } else {
                                    Log.d("MedicalHistoryRepository", "Failed to add vaccine")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ExpandableCard(title: String, icon: ImageVector, expanded: Boolean, onExpandChange: (Boolean) -> Unit, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = UpetBackGroundPrimary
        ),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onExpandChange(!expanded) }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Info",
                        tint = Pink
                    )
                    Text(text = title)
                }
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expanded) "Collapse" else "Expand"
                )
            }

            if (expanded) {
                HorizontalDivider(color = Color.LightGray)
                Box(modifier = Modifier.padding(16.dp)) {
                    content()
                }
            }
        }
    }
}

fun convertDateFormat(inputDate: String): String {
    if(inputDate == "") return inputDate
    val parser = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = parser.parse(inputDate)
    return formatter.format(date!!)
}

@Composable
fun InputDropdownField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    suggestions: List<String>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var filteredSuggestions by remember { mutableStateOf(suggestions) }

    Column(modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                expanded = true
                filteredSuggestions = suggestions.filter { suggestion ->
                    suggestion.contains(it, ignoreCase = true)
                }
            },
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .size(height = 56.dp, width = 300.dp)
                .padding(bottom = 10.dp, start = BorderPadding, end = BorderPadding)
                .border(BorderStroke(2.dp, Pink), shape = RoundedCornerShape(10.dp))
                .background(Color.White, shape = RoundedCornerShape(10.dp))
            ,
            singleLine = true,

            textStyle = TextStyle(
                color = if (value.isNotEmpty()) Color.Black else Color.Gray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = poppinsFamily
            ),

        )
        DropdownMenu(
            expanded = expanded && filteredSuggestions.isNotEmpty(),
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            filteredSuggestions.forEach { suggestion ->
                DropdownMenuItem(
                    onClick = {
                        onValueChange(suggestion)
                        expanded = false
                    },
                    text = { Text(suggestion) }
                )
            }
        }
    }
}