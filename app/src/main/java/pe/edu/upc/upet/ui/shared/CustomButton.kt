package pe.edu.upc.upet.ui.shared

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun CustomButton(text: String, icon: ImageVector? = null, onClick: () -> Unit, ) {
    Button(
        modifier = Modifier
            .fillMaxWidth(),
        //.background(,shape = RoundedCornerShape(10.dp)),
        onClick = onClick
    ) {
        if (icon != null) {
            Icon(imageVector = icon, contentDescription = "$text  Icon")
        }
        Text(
            text = text,
        )
    }
}