package pe.edu.upc.upet.ui.screens.vet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pe.edu.upc.upet.ui.shared.CustomButton
import pe.edu.upc.upet.ui.shared.PasswordTextField
import pe.edu.upc.upet.ui.shared.TopBar

@Composable
fun VetEditPassword(navController: NavController){
    Scaffold(
        topBar = {
            TopBar(navController = navController, title = "Change Password")
        },
        modifier = Modifier.padding(16.dp)
    ) {paddingValues ->

        val password = remember { mutableStateOf("") }
        val newPassword = remember { mutableStateOf("") }
        val confirmPassword = remember { mutableStateOf("") }

        Column (
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(10.dp, 10.dp),
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ){

            Text(
                text = "Password",
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
            PasswordTextField(input = password, placeholder = "Enter your password")
            Text(
                text = "New Password",
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
            PasswordTextField(input = newPassword, placeholder = "Enter new password")
            Text(
                text = "Repeat New Password",
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
            PasswordTextField(input = confirmPassword, placeholder = "Repeat your new password")

            CustomButton(text = "Reset Password", onClick = {
                navController.popBackStack()
            })
        }
    }
}