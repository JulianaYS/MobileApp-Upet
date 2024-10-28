package pe.edu.upc.upet.ui.shared

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.upet.ui.theme.BorderPadding
import pe.edu.upc.upet.ui.theme.poppinsFamily

@Composable
fun TextTitle( text:String ) {
    Text(
        text = text,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        modifier = Modifier.padding(start = BorderPadding, end = BorderPadding)
    )
}

@Composable
fun TextSubTitle( text:String ) {
    Text(
        text = text,
        fontSize = 14.sp,
        modifier = Modifier.padding(start = BorderPadding, end = BorderPadding)
    )
}

@Composable
fun TextNormal(text: String, color: Color = Color.Gray) {
    Text(
        text = text,
        style = TextStyle(
            color = color,
            fontSize = 15.sp,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Normal
        ),
    )
}

@Composable
fun TextSemiBold(text: String, color: Color = MaterialTheme.colorScheme.primary) {
    Text(
        text = text,
        style = TextStyle(
            color = color,
            fontSize = 20.sp,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.SemiBold
        ),
    )
}

@Composable
fun TextSubtitle2(text: String) {
    Text(
        modifier = Modifier.padding(bottom = 15.dp),
        text = text,
        style = TextStyle(
            color = Color.Black,
            fontSize = 20.sp,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.SemiBold
        ),
    )
}
