package pe.edu.upc.upet.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.upet.ui.theme.Blue1
import pe.edu.upc.upet.ui.theme.poppinsFamily

@Composable
fun IconAndTextHeader(onBackClick: () -> Unit, icon: ImageVector, text : String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0B1C3F))
            .padding(bottom = 25.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .weight(0.1f)
                .padding(top = 8.dp, start = 16.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.White)
                .size(35.dp, 35.dp)
        ) {
            Icon(
                imageVector = icon,
                "Back",
                modifier =  Modifier.fillMaxSize(1f),
                tint = Blue1
            )
        }
        Text(
            text = text,
            modifier = Modifier
                .padding(top = 13.dp, start = 60.dp)
                .weight(0.5f)
                .fillMaxWidth(),
            style = TextStyle(
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.SemiBold
            ),
        )
    }
}
