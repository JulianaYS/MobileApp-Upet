package pe.edu.upc.upet.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Dialog(
    message: String,
    showError: MutableState<Boolean>,
    backgroundColor: Color = Color.Red,
    actionLabel: String = "Dismiss"
) {
    if (showError.value) {
        Box(modifier = Modifier
            .padding(16.dp)
            .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Snackbar(
                action = {
                    TextButton(onClick = { showError.value = false }) {
                        Text(text = actionLabel)
                    }
                }
            ) {
                Text(text = message)
            }
        }
    }
}