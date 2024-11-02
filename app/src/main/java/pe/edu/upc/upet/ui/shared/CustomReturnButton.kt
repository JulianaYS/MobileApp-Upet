package pe.edu.upc.upet.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pe.edu.upc.upet.ui.theme.Blue1
import pe.edu.upc.upet.ui.theme.Pink
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import pe.edu.upc.upet.ui.theme.BorderPadding

@Composable
fun CustomReturnButton(navController: NavController) {
    IconButton(
        onClick = { navController.popBackStack() },
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .background(Pink)
    ) {
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            "Back",
            modifier = Modifier.fillMaxSize(1f),
            tint = Color.White
        )
    }
}