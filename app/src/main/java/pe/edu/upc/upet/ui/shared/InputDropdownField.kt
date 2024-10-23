package pe.edu.upc.upet.ui.shared

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.upet.ui.theme.BorderPadding
import pe.edu.upc.upet.ui.theme.Gray1
import pe.edu.upc.upet.ui.theme.Pink
import pe.edu.upc.upet.ui.theme.poppinsFamily

@Composable
fun InputDropdownField(
    label: String,
    options: List<String>,
    selectedOption: MutableState<String>
) {

    val commonPadding = BorderPadding

    LabelTextField(label, commonPadding)

    DropdownMenuBox(options = options, selectedOption = selectedOption, paddingStart = 16, paddingEnd = 16, fontSize = 12)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuBox(
    options: List<String>,
    selectedOption: MutableState<String>,
    paddingStart: Int,
    paddingEnd: Int,
    fontSize: Int
) {
    val expanded = remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = {
            expanded.value = !expanded.value
        }) {
        Box (modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded.value = true }
            .padding(start = paddingStart.dp)
        ){
            OutlinedTextField(
                value = selectedOption.value,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    Icon(imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null, tint = Pink
                    )
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .width(width = 400.dp)
                    .padding(bottom = 10.dp, end = paddingEnd.dp)
                    .border(BorderStroke(2.dp, Pink), shape = RoundedCornerShape(10.dp))
                    .background(Color.White, shape = RoundedCornerShape(10.dp)),
                shape = RoundedCornerShape(10.dp),
                textStyle = TextStyle(
                    color = if (selectedOption.value.isNotEmpty()) Color.Black else Gray1,
                    fontSize = fontSize.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal
                )
            )
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
                modifier = Modifier
                    .width(width = 380.dp)
                    .background(Color.White, shape = RoundedCornerShape(10.dp))
                    .exposedDropdownSize()
                    .border(BorderStroke(2.dp, Pink), shape = RoundedCornerShape(10.dp))
            ) {
                options.forEach {
                    DropdownMenuItem(
                        text = { Text(it,
                            color = if (selectedOption.value.isNotEmpty()) Color.Black else Gray1,
                            fontSize = fontSize.sp,
                            fontFamily = poppinsFamily,
                            fontWeight = FontWeight.Normal
                        ) },
                        onClick = {
                            selectedOption.value = it
                            expanded.value = false },
                    )
                    if (it != options.last()) {
                        HorizontalDivider(thickness = 0.dp, color = Pink)
                    }
                }
            }
        }
    }
}