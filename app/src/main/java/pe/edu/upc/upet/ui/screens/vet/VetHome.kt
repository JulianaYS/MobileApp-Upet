package pe.edu.upc.upet.ui.screens.vet

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.feature_appointment.data.repository.AppointmentRepository
import pe.edu.upc.upet.feature_appointment.domain.Appointment
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.screens.petowner.NewsSection
import pe.edu.upc.upet.ui.screens.petowner.getVet
import pe.edu.upc.upet.ui.screens.petowner.vetclinic.capitalizeFirstLetter
import pe.edu.upc.upet.ui.theme.Blue1
import pe.edu.upc.upet.ui.theme.BorderPadding

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VetHome(navController: NavHostController) {
   var filteredAppointments by remember { mutableStateOf(listOf<Appointment>()) }

    val vet = getVet() ?: return

   LaunchedEffect(vet.id) {
        AppointmentRepository().getUpcomingAppointmentsByVeterinarianId(vet.id) { vetAppointments ->
            filteredAppointments = vetAppointments.take(4)
        }
    }

    Scaffold(
        modifier = Modifier
    ) {paddingValues ->

        Column(modifier = Modifier.padding(paddingValues)) {
            UserSectionVet(navController)
            Spacer(modifier = Modifier.height(10.dp))

            NewsSection(description1 = "Acceso en tiempo real a indicadores de salud", description2 = "¡Optimiza la atención a tus pacientes!" )

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Upcoming Booking",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                color = Color.Black,
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Column {
                LazyColumn {
                    items(filteredAppointments.size) {
                        AppointmentCardVet(filteredAppointments[it], navController)
                    }
                }
            }
        }
    }
}


@Composable
fun UserSectionVet(navController: NavHostController) {
    val vet = getVet() ?: return
    Log.d("UserSection", "Vet: $vet")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = BorderPadding, end = BorderPadding, top = BorderPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            GlideImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .border(1.dp, Blue1, RoundedCornerShape(50.dp))
                    .clickable { navController.navigate(Routes.VetProfile.route)},
                imageModel = { vet.imageUrl },
            )
            Column(modifier = Modifier.padding(start = 20.dp)) {
                val greeting = "Hello, ${capitalizeFirstLetter(vet.name)}"
                Text(
                    text = greeting,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = Blue1
                )
                Text(
                    text = "Welcome!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Blue1
                )
            }
        }
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Notifications",
            tint = Blue1
        )
    }
}