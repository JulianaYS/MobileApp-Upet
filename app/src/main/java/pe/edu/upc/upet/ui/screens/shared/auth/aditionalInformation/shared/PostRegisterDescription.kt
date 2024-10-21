package pe.edu.upc.upet.ui.screens.shared.auth.aditionalInformation.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.upet.ui.theme.BorderPadding
import pe.edu.upc.upet.ui.theme.UpetBackGroundPrimary
import pe.edu.upc.upet.ui.theme.poppinsFamily

@Composable
fun PostRegister(description: @Composable ()->Unit, form : @Composable ()->Unit) {
    Scaffold(modifier = Modifier.padding(10.dp) ){ paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(UpetBackGroundPrimary)
                .padding(10.dp, 10.dp)
        ) {
            description()
            Spacer(modifier = Modifier.height(15.dp))
            form()
        }
    }
}
@Composable
fun PostRegisterDescription(title: String, description: String) {
    Column(
        modifier= Modifier.padding(BorderPadding)

    ) {
        Text(text = title,
            style = TextStyle(
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(modifier = Modifier.height(22.dp))
        Text(text = description,
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Medium
            )
        )
    }
}