package pe.edu.upc.upet.ui.shared

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.upet.ui.theme.BorderPadding
import pe.edu.upc.upet.ui.theme.Gray1
import pe.edu.upc.upet.ui.theme.Pink
import pe.edu.upc.upet.ui.theme.poppinsFamily

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InputDate(text: String, onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val selectedDate = remember { mutableStateOf("") }
    val commonPadding = BorderPadding

    LabelTextField(text, commonPadding)

    InputTextFieldForDate(
        input = selectedDate,
        placeholder = "Select date"
    ) {
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val date = "$dayOfMonth/${month + 1}/$year"
                onDateSelected(date)
                selectedDate.value = date
            }
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            context,
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}

@Composable
fun InputTextFieldForDate(input: MutableState<String>, placeholder: String, onIconClick: () -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .size(height = 56.dp, width = 300.dp)
            .padding(bottom = 10.dp, start = BorderPadding, end = BorderPadding)
            .border(BorderStroke(2.dp, Pink), shape = RoundedCornerShape(10.dp))
            .background(Color.White, shape = RoundedCornerShape(10.dp)), // Rounded corners
        placeholder = {
            TextPlaceHolder(placeholder = placeholder)
        },
        shape = RoundedCornerShape(10.dp),
        textStyle = TextStyle(
            color = if (input.value.isNotEmpty()) Color.Black else Gray1,
            fontSize = 12.sp,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Normal
        ),
        value = input.value,
        onValueChange = { newValue ->
            if (newValue.matches(Regex("\\d{2}/\\d{2}/\\d{4}"))) {
                input.value = newValue
            }
        },
        trailingIcon = {
            IconButton(onClick = onIconClick) {
                Icon(Icons.Default.DateRange, contentDescription = "Select date", tint = Color(0xFFFF6262))
            }
        },
        readOnly = true
    )
}