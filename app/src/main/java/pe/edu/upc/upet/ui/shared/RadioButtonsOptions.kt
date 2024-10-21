package pe.edu.upc.upet.ui.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.upet.ui.screens.shared.auth.signup.ToggleableInfo
import pe.edu.upc.upet.ui.theme.Blue1
import pe.edu.upc.upet.ui.theme.Pink
import pe.edu.upc.upet.ui.theme.poppinsFamily

@Composable
fun RadioButtonsOptions(
    option1: String,
    option2: String ,
    selectedOption: MutableState<Int>
) {
    val radioButtons = remember {
        mutableStateListOf(
            ToggleableInfo(
                isChecked = true,
                text = option1
            ),
            ToggleableInfo(
                isChecked = false,
                text = option2
            )
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        radioButtons.forEachIndexed { index, info ->
            Row(
                modifier = Modifier
                    .clickable {
                        radioButtons.replaceAll {
                            it.copy(
                                isChecked = it.text == info.text
                            )
                        }
                        selectedOption.value =
                            if (index == 0) 1 else 2 // Asigna 1 si es la opción 1, 2 si es la opción 2
                    }
                    .padding(end = 10.dp)
            ) {
                RadioButton(
                    selected = info.isChecked,
                    onClick = {
                        radioButtons.replaceAll {
                            it.copy(
                                isChecked = it.text == info.text
                            )
                        }
                        selectedOption.value = if (index == 0) 1 else 2 // Asigna 1 si es la opción 1, 2 si es la opción 2
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Pink,
                        unselectedColor = Pink
                    ),
                )
                Text(
                    text = info.text,
                    style = TextStyle(
                        color = Blue1,
                        fontSize = 12.sp,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.padding(top = 15.dp)
                )
            }
        }
    }
}