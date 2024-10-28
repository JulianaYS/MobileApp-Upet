package pe.edu.upc.upet.ui.screens.shared.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.screens.shared.auth.aditionalInformation.shared.TimePickerInput
import pe.edu.upc.upet.ui.shared.AuthButton
import pe.edu.upc.upet.ui.shared.AuthInputTextField
import pe.edu.upc.upet.ui.shared.CustomReturnButton
import pe.edu.upc.upet.ui.shared.InputDate
import pe.edu.upc.upet.ui.shared.LabelTextField
import pe.edu.upc.upet.ui.shared.SuccessDialog
import pe.edu.upc.upet.ui.shared.TopBar
import pe.edu.upc.upet.ui.theme.BorderPadding
import pe.edu.upc.upet.ui.theme.poppinsFamily
import pe.edu.upc.upet.utils.TokenManager
import java.text.SimpleDateFormat
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateNotification(navController: NavHostController) {
    val (id, _, _) = TokenManager.getUserIdAndRoleFromToken() ?: error("Error obteniendo el userId y userRole desde el token")
    val showSuccessDialog = remember { mutableStateOf(false) }
    val showErrorSnackbar = remember { mutableStateOf(false) }
    val snackbarMessage = remember { mutableStateOf("") }

    val type = remember { mutableStateOf("") }
    val message = remember { mutableStateOf("") }
    val datee = remember { mutableStateOf("") }
    val time = remember { mutableStateOf("") }

    val context = LocalContext.current


    Scaffold(modifier = Modifier,
        topBar = {
            TopBar(navController = navController, title = "Schedule Notification")
        }
    ) { paddingValues ->
        LazyColumn {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(top= BorderPadding),
                    verticalArrangement = Arrangement.spacedBy(10.dp)

                ) {

                    AuthInputTextField(
                        input = type,
                        placeholder = "Enter type of notification",
                        label = "Type of message",
                    )

                    AuthInputTextField(
                        input = message,
                        placeholder = "Enter message",
                        label = "Message"
                    )

                    InputDate(text = "Date", onDateSelected = { date ->
                        datee.value = date
                    })

                    Row(
                        modifier = Modifier.padding(horizontal = 13.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LabelTextField(label = "Select Hour: ", commonPadding = 4.dp)
                        TimePickerInput(time = time)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Spacer(modifier = Modifier.height(16.dp))

                    AuthButton(text = "Schedule", onClick = {
                        notificacionprogramada(
                            context,
                            type.value,
                            message.value,
                            datee.value,
                            time.value,
                            showSuccessDialog
                        )
                    })

                    /*Button(onClick = {
                    val datetimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                    val dateTime = LocalDateTime.parse("${datee.value} ${time.value}", datetimeFormat)
                    val backendFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

                    // Formatea el objeto LocalDateTime al formato de tu backend
                    val backendDateTime = dateTime.format(backendFormat)

                    NotificationRepository().createNotification(
                        NotificationRequest(
                            petOwnerId = id,
                            type = type.value,
                            message = message.value,
                            datetime = backendDateTime
                        )
                    ) { success ->
                        if (success) {
                            notificacionprogramada(context, type.value, message.value, datee.value, time.value)
                            showSuccessDialog.value = true
                        } else {
                            snackbarMessage.value = "Failed to create notification."
                            showErrorSnackbar.value = true
                        }
                    }
                }) {
                            Text(text = "Create notification")
                }*/

                }
            }
        }
    }


    if (showSuccessDialog.value) {
        SuccessDialog(
            onDismissRequest = {
                showSuccessDialog.value = false
                navController.navigate(Routes.OwnerHome.route)
            },
            titleText = "Registered Notification",
            messageText = "Your notification has been successfully registered.",
            buttonText = "OK"
        )
    }

    if (showErrorSnackbar.value) {
        Snackbar(
            action = {
                Button(onClick = { showErrorSnackbar.value = false }) {
                    Text("Close")
                }
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(snackbarMessage.value)
        }
    }
}


fun notificacionprogramada(context: Context, type: String, message: String, datee: String, time: String, showSuccessDialog: MutableState<Boolean>){
    val NOTIFICATION_ID=10
    val intent = Intent(context, ScheduledNotification::class.java)
    intent.putExtra("type", type)
    intent.putExtra("message", message)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        NOTIFICATION_ID,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val formato = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val fechaHora = formato.parse("$datee $time")
    val temp = fechaHora.time

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (!alarmManager.canScheduleExactAlarms()) {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            context.startActivity(intent)


        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                temp,
                pendingIntent
            )
            showSuccessDialog.value = true
        }
    } else {
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            temp,
            pendingIntent
        )
        showSuccessDialog.value = true
    }
}
