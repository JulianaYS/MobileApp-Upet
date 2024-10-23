package pe.edu.upc.upet.ui.shared

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.upet.ui.theme.Gray1
import pe.edu.upc.upet.ui.theme.Pink
import pe.edu.upc.upet.ui.theme.poppinsFamily

@Composable
fun ExpandableTextField(
    input: MutableState<String>,
    placeholder: String,
) {
    val cornerSize = 10.dp
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = input.value,
            onValueChange = { input.value = it },
            placeholder = {
                Text(
                    text = placeholder,
                    style = TextStyle(
                        color = Gray1,
                        fontSize = 16.sp,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Normal
                    )
                )
            },
            shape = RoundedCornerShape(cornerSize),
            modifier = Modifier
                .fillMaxWidth()
                .border(BorderStroke(2.dp, Pink), shape = RoundedCornerShape(cornerSize))
                .background(Color.White, shape = RoundedCornerShape(cornerSize)),
            textStyle = TextStyle(
                color = if (input.value.isNotEmpty()) Color.Black else Gray1,
                fontSize = 16.sp,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Normal
            )
        )
    }
}