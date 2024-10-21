package pe.edu.upc.upet.ui.shared

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pe.edu.upc.upet.ui.theme.BorderPadding
import pe.edu.upc.upet.ui.theme.Pink

@Composable
fun DropdownTextField(
    selectedItem: MutableState<String>,
    cornerSize: Dp,
    dropdownItems: List<String>,
    placeholder: String
) {
    var expanded by remember { mutableStateOf(false) }
    var filteredItems by remember { mutableStateOf(dropdownItems) }
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp, start = BorderPadding, end = BorderPadding)

    ){

        OutlinedTextField(
            value = selectedItem.value,
            shape= RoundedCornerShape(cornerSize),
            textStyle= commonTextStyle(selectedItem.value),


            modifier= Modifier.commonModifier(cornerSize, start = 0.dp, end= 0.dp),
            onValueChange = {
                selectedItem.value = it
                filteredItems = dropdownItems.filter { item -> item.contains(
                    it, ignoreCase = true) }
                expanded = true
            },
            placeholder = { TextPlaceHolder(placeholder) },

            )

        if (expanded) {
            LazyColumn(
                modifier = Modifier
                    .background(Color.White)
                    .border(BorderStroke(1.dp, Pink))) {
                items(filteredItems.size) { index ->
                    DropdownMenuItem(
                        text = { Text(text = filteredItems[index], modifier = Modifier.padding(8.dp)) },
                        onClick = {
                            selectedItem.value = filteredItems[index]
                            expanded = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}