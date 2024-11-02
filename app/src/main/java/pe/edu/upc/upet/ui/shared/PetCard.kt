package pe.edu.upc.upet.ui.shared

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.feature_pet.domain.Pet
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.screens.petowner.vetclinic.capitalizeFirstLetter
import java.time.LocalDate
import java.time.Period

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PetCard(navController: NavController, pet: Pet) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF0F6FF),
        ),
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
        ) {
            Log.d("PetCard", "Pet Image URL: ${pet.image_url}")


            GlideImage(imageModel = { pet.image_url },
                modifier = Modifier
                    .padding(10.dp)
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(1.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = capitalizeFirstLetter(pet.name),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = Color.Black
                    )
                    Text(
                        text = "Breed: ${pet.breed}",
                        color = Color.Gray
                    )
                    Text(
                        text = "Age: ${getAge(pet.birthdate)}",
                        color = Color.Gray
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(1.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { navController.navigate(Routes.PetDetails.createRoute(pet.id))},
                        colors = ButtonDefaults.buttonColors(Color(0xFFEB5569)),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("View")
                    }
                    Button(
                        onClick = { navController.navigate(Routes.EditPetDetail.createRoute(pet.id))},
                        colors = ButtonDefaults.buttonColors(Color(0xFFEB5569)),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Edit")
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getAge(birthdate: String) :String?{
    val birth = LocalDate.parse(birthdate)
    val now = LocalDate.now()
    val dif = Period.between(now, birth).years
    val age: String = if(dif == 0){
        Period.between(birth, now).months.toString() + " months"
    } else {
        "$dif years"
    }
    return age
}