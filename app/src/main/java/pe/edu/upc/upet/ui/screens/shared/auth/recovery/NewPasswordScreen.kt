package pe.edu.upc.upet.ui.screens.shared.auth.recovery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.shared.AuthButton
import pe.edu.upc.upet.ui.shared.AuthInputTextField
import pe.edu.upc.upet.ui.shared.CustomReturnButton
import pe.edu.upc.upet.ui.shared.TextFieldType
import pe.edu.upc.upet.ui.shared.TextSubTitle
import pe.edu.upc.upet.ui.shared.TextTitle

@Composable
fun NewPasswordScreen( navController: NavController) {
    Scaffold(modifier = Modifier) { paddingValues ->

        val password = remember { mutableStateOf("") }
        val confirmPassword = remember { mutableStateOf("") }

        Column (
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(10.dp, 10.dp),
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ){
            CustomReturnButton(navController =  navController)

            TextTitle(text = "Set a new Password")

            TextSubTitle(text = "Create a new password. Ensure it differs from previous passwords for security")

            AuthInputTextField(
                input = password,
                placeholder = "Enter your password",
                label = "Password",
                type= TextFieldType.Password
            )

            AuthInputTextField(
                input = confirmPassword,
                placeholder = "Repeat Password",
                label = "Password",
                type= TextFieldType.Password
            )

            AuthButton(text = "Reset Password", onClick = {
                navController.navigate(Routes.SignIn.route)
            })

        }
    }
}