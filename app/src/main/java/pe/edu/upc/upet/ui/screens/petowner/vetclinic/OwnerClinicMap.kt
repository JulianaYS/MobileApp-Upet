package pe.edu.upc.upet.ui.screens.petowner.vetclinic

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import pe.edu.upc.upet.R
import pe.edu.upc.upet.feature_vetClinic.data.repository.VeterinaryClinicRepository
import pe.edu.upc.upet.feature_vetClinic.domain.VeterinaryClinic
import pe.edu.upc.upet.ui.screens.petowner.getOwner
import pe.edu.upc.upet.ui.shared.TopBar
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@Composable
fun OwnerClinicMap(navController: NavController) {
    val petOwner = getOwner() ?: return

    Scaffold(
        topBar = {
            TopBar(navController = navController,
                title = "Nearby Vet Clinics")
        },
        modifier = Modifier
    ) {paddingValues->
        Column(Modifier.padding(paddingValues)) { }
        MapSection(petOwner.location)
    }

}

@Composable
fun MapSection(location: String) {

    val vetClinicRepository = remember { VeterinaryClinicRepository() }
    var vetClinics: List<VeterinaryClinic> by remember { mutableStateOf(emptyList()) }
    var vetClinicsLocations: List<LatLng> by remember { mutableStateOf(emptyList()) }

    LaunchedEffect(key1 = vetClinicRepository) {
        vetClinicRepository.getAllVeterinaryClinics { vetClinicsList ->
            vetClinics = vetClinicsList
        }
    }

    val myLatitude = location.split(",")[0].toDouble()
    val myLongitude = location.split(",")[1].toDouble()

    val myLocation = LatLng(myLatitude, myLongitude)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(myLocation, 15f)
    }
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.TERRAIN))
    }

    for (vetClinic in vetClinics) {
        val clinicLocation = vetClinicTransformLocation(vetClinic)
        vetClinicsLocations += clinicLocation
    }

    GoogleMap(
        modifier = Modifier.fillMaxWidth(),
        cameraPositionState = cameraPositionState,
        properties = properties
    ) {

        Marker(
            state = MarkerState(position = myLocation),
            title = "My Location",
            snippet = "Here I am",
        )

        //add clinic markers but before that, calculate the distance between the clinic and the user
        //the maximum distance is 3 km around the user location
        for(vetClinicLocation in vetClinicsLocations){
            val distance = calculateDistance(myLocation, vetClinicLocation)
            //distance between the user and the clinic is less than 3 km so we show the marker
            if(distance <= 3){
                Marker(
                    state = MarkerState(position = vetClinicLocation),
                    title = "Veterinary Clinic",
                    snippet = "Closes to you",
                    //use the icon form the res drawable folder
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.veterinaria_icon)
                )
            }
        }
    }
}

fun calculateDistance(start: LatLng, end: LatLng): Double {
    val earthRadiusKm = 6371

    val dLat = Math.toRadians(end.latitude - start.latitude)
    val dLon = Math.toRadians(end.longitude - start.longitude)
    val lat1 = Math.toRadians(start.latitude)
    val lat2 = Math.toRadians(end.latitude)

    val a = sin(dLat / 2) * sin(dLat / 2) +
            sin(dLon / 2) * sin(dLon / 2) * cos(lat1) * cos(lat2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    //returns the distance in kilometers
    return earthRadiusKm * c
}

//this function transform the location of a veterinary clinic from a string format: "-12.123123,-77.123123"
// to a LatLng object
// it need to be fixed because some locations doesn't have the correct format and the code will crash
fun vetClinicTransformLocation(veterinaryClinic: VeterinaryClinic): LatLng {
    val clinicLocation = veterinaryClinic.location
    Log.d("clinicLocation", clinicLocation)
    //if location is not with cords format string, we return a default location to avoid the app crash
    if(!clinicLocation.contains(",")){
        return LatLng(-12.122280, -76.986250)
    }

    val clinicLatitude = clinicLocation.split(",")[0].toDouble()
    val clinicLongitude = clinicLocation.split(",")[1].toDouble()
    return LatLng(clinicLatitude, clinicLongitude)
}

