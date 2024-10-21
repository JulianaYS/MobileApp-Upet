package pe.edu.upc.upet.ui.shared

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import pe.edu.upc.upet.ui.theme.BorderPadding

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