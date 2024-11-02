package pe.edu.upc.upet.ui.screens.petowner.pet

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pe.edu.upc.upet.feature_pet.data.repository.PetRepository
import pe.edu.upc.upet.feature_pet.domain.Pet
import pe.edu.upc.upet.ui.shared.PetCard
import pe.edu.upc.upet.ui.shared.TopBar
import pe.edu.upc.upet.utils.TokenManager

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PetList(navController: NavHostController) {

    val petRepository = remember { PetRepository() }
    val petsState = remember {
        mutableStateOf<List<Pet>>(emptyList())
    }

    val (id, _, _) = TokenManager.getUserIdAndRoleFromToken() ?: error("Error obteniendo el userId y userRole desde el token")

    petRepository.getPetsByOwnerId(id){
        petsState.value = it
    }

    Scaffold(
        topBar = {
            TopBar(navController = navController, title = "My Pets")
        },
        modifier = Modifier

    ) {paddingValues->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(petsState.value) { pet ->
                PetSwipeToDelete(navController, pet, deletePet = {
                    petRepository.deletePet(pet.id){
                        if (it){
                            petsState.value = petsState.value.filter { it != pet }
                        }else{
                            Log.d("PetList", "Error al eliminar la mascota")
                        }
                    }
                })
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetSwipeToDelete(navController:NavHostController, pet: Pet, deletePet:() ->Unit) {
    val dismissState = rememberSwipeToDismissBoxState()

    LaunchedEffect(key1 = dismissState.currentValue) {
        if (dismissState.currentValue == SwipeToDismissBoxValue.StartToEnd) {

            deletePet()

        }
        dismissState.reset()
    }
    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = { SwipeToDismissBackground() },
        content = { PetCard(navController = navController , pet = pet) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clip(RoundedCornerShape(20.dp)),
        enableDismissFromEndToStart = false,
        enableDismissFromStartToEnd = true,
    )
}

@Composable
fun SwipeToDismissBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEB5569))
            .padding(horizontal = 15.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Icon(
            Icons.Default.Delete,
            contentDescription = "Delete Pet",
            tint = Color.White
        )
    }
}