package pe.edu.upc.upet.ui.screens.petowner.pet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TagFaces
import androidx.compose.material.icons.outlined.Balance
import androidx.compose.material.icons.outlined.Pets
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.feature_pet.data.remote.GenderEnum
import pe.edu.upc.upet.feature_pet.data.repository.PetRepository
import pe.edu.upc.upet.feature_pet.domain.Pet
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.shared.CustomButton
import pe.edu.upc.upet.ui.shared.CustomReturnButton
import pe.edu.upc.upet.ui.shared.TopBar
import pe.edu.upc.upet.ui.theme.Blue1
import pe.edu.upc.upet.ui.theme.BorderPadding
import pe.edu.upc.upet.ui.theme.Pink
import pe.edu.upc.upet.ui.theme.poppinsFamily

@Composable
fun PetDetail(navController: NavHostController, petId: Int) {
    var pet by remember { mutableStateOf<Pet?>(null) }

    PetRepository().getPetById(petId){
        pet = it
    }

    val petValue = pet ?: return
    data class PetInfo(val title: String, val icon: ImageVector, val content: String)
    fun petResponseToPetInfoList(petResponse: Pet): List<PetInfo> {
        return listOf(
            PetInfo("Breed", Icons.Outlined.Pets, petResponse.breed),
            PetInfo("Species", Icons.Outlined.WbSunny, petResponse.specie),
            PetInfo("Weight", Icons.Outlined.Balance, petResponse.weight.toString()),
            PetInfo("Birthdate", Icons.Outlined.Timer, petResponse.birthdate),
        )
    }
    val petInfoList = petResponseToPetInfoList(petValue)

    Scaffold(modifier = Modifier,
        topBar = {
            TopBarPet(navController = navController, title = petValue.name, gender= petValue.gender)
        },
        ) { paddingValues ->
        LazyColumn {
            item {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxWidth()
                        .fillMaxSize()
                        .background(Blue1),
                    verticalArrangement = Arrangement.spacedBy(13.dp)
                ) {
                    /*Row (modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween){
                        CustomReturnButton(navController = navController)
                        Text(
                            text = petValue.name,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 10.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )

                        IconButton(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            onClick = {  }) {
                            Icon(
                                imageVector = if(petValue.gender == GenderEnum.Male) Icons.Filled.Male else Icons.Filled.Female,
                                contentDescription = petValue.gender.toString()
                            )
                        }
                    }*/

                    println("Gender: ${petValue.gender}")
                    ImageRectangle(petValue.image_url)

                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                            .background(Color.White)
                    ){
                        Column (modifier = Modifier.padding(15.dp, 15.dp)){
                            Row (modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                Text(
                                    text = "General Information",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 24.sp,
                                    color = Color.Black,
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                )
                                IconButton(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    onClick = { }) {
                                    Icon(
                                        Icons.Filled.TagFaces,
                                        "TagFaces",
                                        tint = Color.Black
                                    )
                                }
                            }

                            LazyRow {
                                items(petInfoList) { petInfo ->
                                    PetInformationCard(petInfo.title, petInfo.icon, petInfo.content)
                                }
                            }

                            Text(
                                text = "More details",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 24.sp,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(top =10.dp, bottom = 10.dp)
                            )

                            CustomButton(text = "Add Medical Information") {
                                navController.navigate(Routes.AddReport.createRoute( petValue.id))
                            }
                            CustomButton(text = "Edit Profile") {
                                navController.navigate(Routes.EditPetDetail.createRoute(petValue.id))
                            }
                            CustomButton(text = "Medical History") {
                                navController.navigate(Routes.petMedicalHistory.createRoute(petValue.id))
                            }
                            //}
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ImageRectangle(imageUrl: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        GlideImage(
            modifier = Modifier
                .fillMaxWidth()
                .size(200.dp)
                .clip(shape = RoundedCornerShape(20.dp))
            ,
            imageModel = { imageUrl })
    }
}

@Composable
fun PetInformationCard(title:String, icon: ImageVector, content:String){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .width(180.dp)
            .height(100.dp)
            .padding(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color(0xFF0A2540)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
            Icon(icon, contentDescription = "heart", tint = Color(0xFF0A2540))
        }
        Text(
            text = content,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 4.dp)
                .align(Alignment.CenterHorizontally),
        )
    }
}


@Composable
fun MedicalHistoryCard(title: String, date: String, description: String,icon: ImageVector) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 4.dp)
    )
    {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF0A2540),
                contentColor = Color.White
            ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                // Icon
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color(0xFFFF6D6D), shape = RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Medical services",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = date,
                            fontSize = 14.sp,
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = description,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarPet(
    navController: NavController,
    title: String,
    gender: GenderEnum
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = Color.Black,
        ),
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomReturnButton1(navController = navController)
                Text(
                    text = title,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.SemiBold
                    ),
                    textAlign = TextAlign.Center,
                    letterSpacing = 1.sp,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (gender == GenderEnum.Male) Icons.Filled.Male else Icons.Filled.Female,
                    contentDescription = gender.toString(),
                    tint = Color.Black
                )
            }
        }
    )
}

@Composable
fun CustomReturnButton1(navController: NavController) {
    IconButton(
        onClick = { navController.popBackStack() },
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .background(Pink)
    ) {
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            "Back",
            modifier = Modifier.fillMaxSize(1f),
            tint = Blue1
        )
    }
}
