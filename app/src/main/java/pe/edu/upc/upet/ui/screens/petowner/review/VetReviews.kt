package pe.edu.upc.upet.ui.screens.petowner.review

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pe.edu.upc.upet.feature_review.data.remote.ReviewResponse
import pe.edu.upc.upet.feature_vet.data.repository.VetRepository
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.screens.petowner.ProfileImage
import pe.edu.upc.upet.ui.screens.petowner.vetclinic.capitalizeFirstLetter
import pe.edu.upc.upet.ui.shared.TopBar
import pe.edu.upc.upet.ui.theme.BorderPadding
import pe.edu.upc.upet.ui.theme.Pink
import pe.edu.upc.upet.ui.theme.poppinsFamily
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun VetReviews(navController: NavController, vetId: Int, showFAB: Boolean = true) {
    val vetRepository = remember { VetRepository() }
    val reviewList = remember {
        mutableStateOf<List<ReviewResponse>>(emptyList())
    }

    vetRepository.getVetReviews(vetId,
        onSuccess = { reviews ->
            reviewList.value = reviews
        },
        onError = {
            Log.e("VetReviews", "Error al obtener las reviews")
        }
    )
    Log.e("VetReviews", reviewList.value.toString())

    Scaffold (
        topBar = {
            TopBar(navController = navController, title = "Reviews")
        },
        modifier = Modifier,
        floatingActionButton = {
            if(showFAB){
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Routes.AddReview.createRoute(vetId))
                    },
                    contentColor = Color.White,
                    containerColor = Pink,
                ) {
                    Icon(imageVector = Icons.Default.Add,
                        contentDescription = "Add")
                }
            }
        }
    ){
        paddingValues->
        LazyColumn(modifier = Modifier
            .padding(paddingValues)
            .background(Color.Transparent)) {
            items(reviewList.value) { review ->
                ReviewCard(review)
            }
        }
    }
}

@Composable
fun ReviewCard(review: ReviewResponse){
    Card(modifier = Modifier
        .padding(start = BorderPadding, end = BorderPadding, bottom = 10.dp, top = 8.dp)
        .shadow(elevation = 8.dp, shape = RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White)
    ) {
        Column (modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp)
        ){
            Row (modifier = Modifier.padding(horizontal = 7.dp,vertical = 7.dp),
                verticalAlignment = Alignment.CenterVertically){
                Box(modifier = Modifier.clip(shape = RoundedCornerShape(20.dp)) )
                {
                    ProfileImage(url = review.image_url, 42,40)
                }
                Text(
                    text = capitalizeFirstLetter(review.pet_owner_name),
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 15.sp,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1f)
                )
                Column {
                    Row (modifier = Modifier.padding(start= 120.dp)){
                        Icon(imageVector = Icons.Default.Star,
                            contentDescription = "Estrella",
                            tint = Color(0xFFFFB800))
                        Text(text = review.stars.toString(),color = Color.Gray,
                            modifier = Modifier.padding(start = 5.dp))
                    }
                    Row(modifier = Modifier.padding(start = 100.dp)){
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                            Text(text = formatTimeTo12Hour(review.review_time), color = Color.Gray,
                            )

                        }
                    }
                }
            }
            Text(text = review.description,
                modifier = Modifier.width(300.dp))
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun formatTimeTo12Hour(time: String): String {
    val dateTime = LocalDateTime.parse(time)
    val formatter = DateTimeFormatter.ofPattern("h:mm a")
    return dateTime.format(formatter)
}