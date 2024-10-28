package pe.edu.upc.upet.ui.shared

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import pe.edu.upc.upet.ui.theme.Blue1

@Composable
fun CustomButton(text: String, icon: ImageVector? = null, onClick: () -> Unit, ) {
    Button(
        modifier = Modifier
            .fillMaxWidth(),
        //.background(,shape = RoundedCornerShape(10.dp)),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Blue1)
    ) {
        if (icon != null) {
            Icon(imageVector = icon, contentDescription = "$text  Icon")
        }
        Text(
            text = text,
        )
    }
}


@Composable
fun CustomButton2(
    text: String,
    icon: ImageVector? = null,
    onClick: () -> Unit,
    color: Color = Blue1 // Color predeterminado
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = color) // Corregir el uso de buttonColors
    ) {
        if (icon != null) {
            Icon(imageVector = icon, contentDescription = "$text Icon")
        }
        Text(text = text)
    }
}