package pe.edu.upc.upet.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign // Importar TextAlign

@Composable
fun InputDigit(input: MutableState<String>){
    OutlinedTextField(
        modifier = Modifier
            .width(70.dp)
            .padding(5.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp)), // Rounded corners
        value = input.value,
        onValueChange = { newValue: String -> // Especificar el tipo de parámetro
            if (newValue.length <= 1) {
                input.value = newValue.uppercase() // Enforce uppercase and limit to 1 char
            }
        },
        textStyle = TextStyle(
            color = Color.Black,
            textAlign = TextAlign.Center // Center the text
        ),
        singleLine = true, // Limit to single line
        visualTransformation = VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),

    )
}