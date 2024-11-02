package pe.edu.upc.upet.ui.screens.shared.auth.signup

import android.util.Log
import android.util.Patterns
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.upet.feature_auth.data.remote.UserRequest
import pe.edu.upc.upet.feature_auth.data.remote.UserType
import pe.edu.upc.upet.feature_auth.data.repository.AuthRepository
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.shared.AuthButton
import pe.edu.upc.upet.ui.shared.AuthCheckBox
import pe.edu.upc.upet.ui.shared.AuthHeader
import pe.edu.upc.upet.ui.shared.AuthInputTextField
import pe.edu.upc.upet.ui.shared.AuthTextButton
import pe.edu.upc.upet.ui.shared.Dialog
import pe.edu.upc.upet.ui.shared.RadioButtonsOptions
import pe.edu.upc.upet.ui.shared.TextFieldType
import pe.edu.upc.upet.ui.theme.Blue1
import pe.edu.upc.upet.ui.theme.BorderPadding
import pe.edu.upc.upet.ui.theme.poppinsFamily

@Composable
fun SignUpScreen( navigateTo: (String) -> Unit ){

    Scaffold(modifier = Modifier) { paddingValues->
        val fullName = remember {
            mutableStateOf("")
        }
        val email = remember{
            mutableStateOf("")
        }
        val password = remember{
            mutableStateOf("")
        }
        val checkedState = remember {
            mutableStateOf(false)
        }
        val selectedOption = remember {
            mutableIntStateOf(1)
        }

        val showErrorSnackbar = remember { mutableStateOf(false) }
        val snackbarMessage = remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ){

            Box {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    AuthHeader(texto = "Register")
                    AuthInputTextField(input = fullName, placeholder = "Enter your full name", label ="Full Name" )
                    AuthInputTextField(input = email, placeholder = "Enter your email", label ="Email" )
                    AuthInputTextField(input = password, placeholder = "Enter your password", label ="Password", type = TextFieldType.Password)
                    AuthUserRolCheckBox(selectedOption = selectedOption)
                    Log.d("selectedoption", selectedOption.intValue.toString())
                    AuthCheckBox(checkedState = checkedState)
                    AuthButton(text = "Register", onClick = {
                        if (fullName.value.isEmpty()) {
                            snackbarMessage.value = "You must enter your full name."
                            showErrorSnackbar.value = true
                        } else if (email.value.isEmpty()) {
                            snackbarMessage.value = "You must enter your email."
                            showErrorSnackbar.value = true
                        } else if(!Patterns.EMAIL_ADDRESS.matcher(email.value).matches()){
                            snackbarMessage.value = "You must enter a valid email."
                            showErrorSnackbar.value = true
                        } else if (password.value.isEmpty()) {
                            snackbarMessage.value = "You must enter your password."
                            showErrorSnackbar.value = true
                        } else if (!checkedState.value) {
                            snackbarMessage.value = "You must accept the Terms and Conditions."
                            showErrorSnackbar.value = true
                        } else {
                            registerLogicButton(
                                userRequest = UserRequest(
                                    name = fullName.value,
                                    email = email.value,
                                    password = password.value,
                                    userType = if (selectedOption.intValue == 1) UserType.Vet else UserType.Owner
                                ),
                                navigateTo = {
                                    Log.d("SuccesSignUp", "User registered")
                                    navigateTo(Routes.PostRegister.route)
                                }
                            )
                        }
                    })

                    AuthTextButton(text= "Already member?",
                        clickableText ="Login",
                        onClickClickableText = {
                            navigateTo(Routes.SignIn.route)
                        } )
                }

                Dialog(message = (snackbarMessage.value), showError = showErrorSnackbar )
            }
        }
    }
}

@Composable
fun AuthUserRolCheckBox( selectedOption: MutableState<Int> = mutableIntStateOf(1)){
    Column (
        modifier = Modifier.padding(
            start = BorderPadding,
            end = BorderPadding,
            top = 4.dp,
            bottom = 4.dp
        )
    ){
        Text(text ="Are you a ...", style = TextStyle(
            color = Blue1,
            fontSize = 12.sp,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Medium
        )
        )
        RadioButtonsOptions(
            option1 = "Veterinarian",
            option2 = "Pet Owner",
            selectedOption = selectedOption
        )
    }

}
data class ToggleableInfo(
    val isChecked: Boolean,
    val text: String
)


fun registerLogicButton(authRepository: AuthRepository = AuthRepository(), userRequest: UserRequest,
                        navigateTo: () -> Unit){
    authRepository.signUp(userRequest) {
        Log.d("Register", "User registered")
        navigateTo()
    }
}