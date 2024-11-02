package pe.edu.upc.upet.ui.screens.petowner.review

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pe.edu.upc.upet.feature_petOwner.data.repository.PetOwnerRepository
import pe.edu.upc.upet.feature_petOwner.domain.PetOwner
import pe.edu.upc.upet.feature_review.data.remote.ReviewRequest
import pe.edu.upc.upet.feature_review.data.repository.ReviewRepository
import pe.edu.upc.upet.ui.shared.AuthButton
import pe.edu.upc.upet.ui.shared.AuthInputTextField
import pe.edu.upc.upet.ui.shared.CustomReturnButton
import pe.edu.upc.upet.ui.shared.TextFieldType
import pe.edu.upc.upet.ui.shared.TopBar
import pe.edu.upc.upet.ui.theme.BorderPadding
import pe.edu.upc.upet.ui.theme.poppinsFamily
import pe.edu.upc.upet.utils.TokenManager

@Composable
fun AddReview(navController: NavController, vetId: Int) {
    val (id, _, _) = TokenManager.getUserIdAndRoleFromToken() ?: error("Error obteniendo el userId y userRole desde el token")

    var owner by remember { mutableStateOf<PetOwner?>(null) }
    PetOwnerRepository().getPetOwnerById(id) { fetchedOwner ->
        owner = fetchedOwner
    }

    val reviewContent = remember { mutableStateOf("") }
    val reviewRating = remember { mutableStateOf("") }

    var snackbarMessage by remember { mutableStateOf("") }

    Scaffold(modifier = Modifier,
        topBar = {
            TopBar(navController = navController, title = "Add Review")
        }
    ) { paddingValues ->
        LazyColumn {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(top = BorderPadding
                        ),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {

                    AuthInputTextField(
                        input = reviewContent,
                        placeholder = "Enter your review",
                        label = "Review",
                    )
                    AuthInputTextField(
                        input = reviewRating,
                        placeholder = "Enter your rating",
                        label = "Rating",
                        type = TextFieldType.Phone
                    )

                    AuthButton(text = "Submit Review", onClick = {
                        val reviewRequest = ReviewRequest(
                            stars = reviewRating.value.toInt(),
                            description = reviewContent.value,
                            veterinarianId = vetId,
                        )
                        ReviewRepository().createReview(
                            owner?.id ?: -1,
                            reviewRequest,
                            onSuccess = {
                                navController.popBackStack()
                            },
                            onError = {
                                snackbarMessage = "Error al Crear el review"
                            })
                    })
                    if (snackbarMessage.isNotBlank()) {
                        Text(
                            text = snackbarMessage,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                }
            }
        }
    }
}
