package pe.edu.upc.upet.ui.screens.vet

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pe.edu.upc.upet.feature_vetClinic.data.repository.VeterinaryClinicRepository
import pe.edu.upc.upet.ui.shared.CustomButton
import pe.edu.upc.upet.ui.shared.TopBar
import pe.edu.upc.upet.ui.theme.BorderPadding

@Composable
fun GeneratePassword(navController: NavController, vetClinicId: Int) {
    val context = LocalContext.current

    var generatedPassword by remember { mutableStateOf("") }
    var showPasswordField by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopBar(navController = navController, title = "Generate Password") },

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Text(
                text = "This password allows another veterinarian to join the current veterinary Clinic in the application.",
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(top= BorderPadding)

            )


            TextField(
                value = generatedPassword,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {},
                readOnly = true,
                label = { Text("Generated Password") },
                trailingIcon = {
                    IconButton(onClick = {
                        val clipboardManager =
                            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip =
                            ClipData.newPlainText("Generated Password", generatedPassword)
                        clipboardManager.setPrimaryClip(clip)
                    }) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "Copy Password")
                    }
                }
            )


            CustomButton(
                text = "Generate Password",
                icon = Icons.Default.Key,
                onClick = {
                    VeterinaryClinicRepository().generatePassword(vetClinicId) {
                        if (it != null) {
                            generatedPassword = it
                            showPasswordField = true
                        }
                    }
                }
            )
        }
    }
}