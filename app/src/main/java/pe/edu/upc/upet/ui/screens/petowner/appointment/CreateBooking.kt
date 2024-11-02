package pe.edu.upc.upet.ui.screens.petowner.appointment

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pe.edu.upc.upet.ui.shared.TopBar

@Composable
fun CreateBooking(navController: NavController) {
    val clinics = listOf("Clinic A", "Clinic B", "Clinic C") // Reemplazar con datos reales
    var selectedClinic by remember { mutableStateOf<String?>(null) }
    val specialists = listOf("Specialist 1", "Specialist 2", "Specialist 3") // Reemplazar con datos reales

    Scaffold(
        topBar = {
            TopBar(navController = navController,
                title = "Select Clinic and Specialist") }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Text(
                text = "Select Clinic",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(16.dp)
            )
            /*LazyColumn(modifier = Modifier.weight(1f)) {
                items(clinics) { clinic ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable { selectedClinic = clinic },
                        elevation = 4.dp
                    ) {
                        Text(
                            text = clinic,
                            modifier = Modifier.padding(16.dp),
                            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
            if (selectedClinic != null) {
                Text(
                    text = "Select Specialist",
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(16.dp)
                )
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(specialists) { specialist ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable { navController.navigate("bookAppointment/$specialist") },
                            elevation = 4.dp
                        ) {
                            Text(
                                text = specialist,
                                modifier = Modifier.padding(16.dp),
                                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }*/

        }
    }
}