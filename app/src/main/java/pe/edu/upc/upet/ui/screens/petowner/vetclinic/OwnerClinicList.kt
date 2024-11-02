package pe.edu.upc.upet.ui.screens.petowner.vetclinic

import android.content.Context
import android.location.Geocoder
import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.upet.feature_vetClinic.data.repository.VeterinaryClinicRepository
import pe.edu.upc.upet.feature_vetClinic.domain.VeterinaryClinic
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.shared.TopBar
import pe.edu.upc.upet.ui.theme.Blue1
import pe.edu.upc.upet.ui.theme.BorderPadding
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun OwnerClinicList(navController: NavHostController) {
    val vetClinicRepository = remember { VeterinaryClinicRepository() }
    var vetClinics: List<VeterinaryClinic> by remember { mutableStateOf(emptyList()) }
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(key1 = vetClinicRepository) {
        vetClinicRepository.getAllVeterinaryClinics { vetClinicsList ->
            vetClinics = vetClinicsList
        }
    }

    Scaffold(
        topBar = {
            TopBar(navController = navController, title = "Veterinary Clinics")
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Routes.OwnerClinicMap.route) },
                containerColor = Blue1,
                contentColor = Color.White
            ){
                Icon(Icons.Default.Map, contentDescription = "Map Icon")
            }
        },
        modifier = Modifier
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            SearchField(searchQuery = searchQuery, onValueChange = {
                searchQuery = it
            })
            vetClinics.filter { it.name.contains(searchQuery, ignoreCase = true) }
                .forEach { vetClinic ->
                    Spacer(modifier = Modifier.height(18.dp))
                    VetClinicCard(navController, vetClinic)
                }
        }
    }

}

@Composable
fun SearchField(searchQuery: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        placeholder = { Text("Search") },
        singleLine = true,
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.White) },
        textStyle = LocalTextStyle.current.copy(color = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(BorderPadding)
            .clip(RoundedCornerShape(10.dp))
            .border(2.dp, Color(0xFFE91E63), RoundedCornerShape(10.dp))
    )
}



fun formatTime(time: String): String {
    val inputFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val date = inputFormat.parse(time)
    return outputFormat.format(date)
}

fun capitalizeFirstLetter(text: String): String {
    return text.split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }
}


suspend fun getStreetNameFromCoordinates(location: String, context: Context): String {
    return try {
        val geocoder = Geocoder(context, Locale.getDefault())
        val coordinates = location.split(",")
        val latitude = coordinates[0].toDouble()
        val longitude = coordinates[1].toDouble()
        val addresses = withContext(Dispatchers.IO) {
            geocoder.getFromLocation(latitude, longitude, 1)
        }
        if (!addresses.isNullOrEmpty()) {
            addresses[0].getAddressLine(0)
        } else {
            "Unknown location"
        }
    } catch (e: NumberFormatException) {
        Log.e("getStreetNameFromCoordinates", "Invalid coordinates: $location", e)
        "Invalid coordinates"
    } catch (e: Exception) {
        Log.e("getStreetNameFromCoordinates", "Error getting address: $location", e)
        "Error getting address"
    }
}
@Composable
fun VetClinicCard(navController: NavController, veterinaryClinic: VeterinaryClinic) {
    val context = LocalContext.current
    var streetName by remember { mutableStateOf("Loading...") }

    LaunchedEffect(veterinaryClinic.location) {
        streetName = getStreetNameFromCoordinates(veterinaryClinic.location, context)
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF0F6FF),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable {
                navController.navigate(Routes.OwnerClinicDetails.createRoute(veterinaryClinic.id))
            }
            .padding(start = BorderPadding, end = BorderPadding)
    ) {
        Row(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(120.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {
            GlideImage(
                imageModel = { veterinaryClinic.image_url },
                modifier = Modifier
                    .size(100.dp)
                    .padding(start = 8.dp)
                    .clip(RoundedCornerShape(15.dp)),
                imageOptions = ImageOptions(contentScale = ContentScale.Crop)
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = capitalizeFirstLetter(veterinaryClinic.name),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = Color.Black
                )
                Column(modifier = Modifier.padding(top = 7.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location Icon",
                            tint = Blue1,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = streetName,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = "Schedule Icon",
                            tint = Blue1,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = " ${formatTime(veterinaryClinic.office_hours_start) + " - " + formatTime(veterinaryClinic.office_hours_end)}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

/*
@Composable
fun getStreetNameFromCoordinates(location: String): String {
    val context = LocalContext.current
    val geocoder = remember { Geocoder(context, Locale.getDefault()) }
    val coordinates = location.split(",")
    val latitude = coordinates[0].toDouble()
    val longitude = coordinates[1].toDouble()
    val addresses = geocoder.getFromLocation(latitude, longitude, 1)
    return if (!addresses.isNullOrEmpty()) {
        addresses[0].getAddressLine(0)
    } else {
        "Unknown location"
    }
}
/*
@Composable
fun getStreetNameFromCoordinates(location: String): String {
    val context = LocalContext.current
    val geocoder = remember { Geocoder(context, Locale.getDefault()) }
    val coordinates = location.split(",")
    val latitude = coordinates[0].toDouble()
    val longitude = coordinates[1].toDouble()
    val addresses = geocoder.getFromLocation(latitude, longitude, 1)
    return if (!addresses.isNullOrEmpty()) {
        val address = addresses[0]
        val addressParts = address.getAddressLine(0).split(",")
        val streetAndDistrict = addressParts.take(2).joinToString(", ")
        streetAndDistrict.replace(Regex("\\d{5}"), "").trim()
    } else {
        "Unknown location"
    }
}*/