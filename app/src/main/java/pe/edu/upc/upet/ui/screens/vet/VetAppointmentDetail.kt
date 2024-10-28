package pe.edu.upc.upet.ui.screens.vet

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pe.edu.upc.upet.feature_appointment.data.repository.AppointmentRepository
import pe.edu.upc.upet.feature_appointment.domain.Appointment
import pe.edu.upc.upet.feature_pet.data.repository.PetRepository
import pe.edu.upc.upet.feature_pet.domain.Pet
import pe.edu.upc.upet.feature_petOwner.data.repository.PetOwnerRepository
import pe.edu.upc.upet.feature_petOwner.domain.PetOwner
import pe.edu.upc.upet.feature_vet.data.repository.VetRepository
import pe.edu.upc.upet.feature_vet.domain.Vet
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.screens.petowner.appointment.AppointmentScheduleInfo
import pe.edu.upc.upet.ui.screens.petowner.appointment.OwnerInformation
import pe.edu.upc.upet.ui.screens.petowner.appointment.PatientInformation
import pe.edu.upc.upet.ui.screens.petowner.appointment.VetInformation
import pe.edu.upc.upet.ui.screens.petowner.getRole
import pe.edu.upc.upet.ui.shared.CustomButton
import pe.edu.upc.upet.ui.shared.TextSubtitle2
import pe.edu.upc.upet.ui.shared.TopBar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VetAppointmentDetail(navController: NavHostController, appointmentId: Int) {
    var appointment by remember { mutableStateOf<Appointment?>(null) }
    var vet by remember { mutableStateOf<Vet?>(null) }
    var pet by remember { mutableStateOf<Pet?>(null) }
    var ownerPet by remember { mutableStateOf<PetOwner?>(null) }

    LaunchedEffect(key1 = appointmentId) {
        AppointmentRepository().getAppointmentById(appointmentId) {
            appointment = it
        }
    }
    appointment ?: return

    LaunchedEffect(key1 = appointment?.veterinarianId) {
        VetRepository().getVetById(appointment!!.veterinarianId) {
            vet = it
        }
    }

    vet ?: return

    LaunchedEffect(key1 = appointment?.petId) {
        PetRepository().getPetById(appointment!!.petId) {
            pet = it
        }
    }

    pet ?: return

    LaunchedEffect(key1 = pet?.petOwnerId) {
        PetOwnerRepository().getPetOwnerById(pet!!.petOwnerId) {
            ownerPet = it
        }
    }

    ownerPet ?: return

    Scaffold(
        topBar = {
            TopBar(
                title = "Appointment Details", navController = navController
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Column {

                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            TextSubtitle2("Schedule Appointment")

            AppointmentScheduleInfo(
                appointment!!.day,
                appointment!!.startTime, appointment!!.endTime
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextSubtitle2("Patient Information")

            PatientInformation(pet!!, appointment!!, navController)

            Spacer(modifier = Modifier.height(20.dp))

            if(getRole() == "Vet"){
                TextSubtitle2("Owner Information")

                OwnerInformation(ownerPet!!, navController)

                Spacer(modifier = Modifier.height(20.dp))

                CustomButton(text = "Add report") {
                    navController.navigate(Routes.AddReport.createRoute( pet!!.id))
                }
            }else{
                TextSubtitle2("Veterinary Information")

                VetInformation(vet!!, navController)
            }
        }
    }
}