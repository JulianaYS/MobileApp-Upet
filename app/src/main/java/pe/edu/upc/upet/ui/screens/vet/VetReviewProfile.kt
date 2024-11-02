package pe.edu.upc.upet.ui.screens.vet

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pe.edu.upc.upet.feature_review.data.remote.ReviewResponse
import pe.edu.upc.upet.feature_vet.data.repository.VetRepository
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.screens.petowner.getVet
import pe.edu.upc.upet.ui.screens.petowner.review.ReviewCard
import pe.edu.upc.upet.ui.shared.TopBar

@Composable
fun VetReviewProfile(navController: NavHostController, showFAB: Boolean = false) {
    val vet = getVet() ?: return

    val vetRepository = remember { VetRepository() }
    val reviewList = remember {
        mutableStateOf<List<ReviewResponse>>(emptyList())
    }

    vetRepository.getVetReviews(vet.id,
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
        floatingActionButton = {
            if(showFAB){
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Routes.AddReview.createRoute(vet.id))
                    },
                    contentColor = Color(0xFF000000),
                ) {
                    Icon(imageVector = Icons.Default.Add,
                        contentDescription = "Add")
                }
            }

        }
    ){
            paddingValues->
        LazyColumn(modifier = Modifier.padding(paddingValues).background(Color.Transparent)) {
            items(reviewList.value) { review ->
                ReviewCard(review)
            }
        }
    }
}