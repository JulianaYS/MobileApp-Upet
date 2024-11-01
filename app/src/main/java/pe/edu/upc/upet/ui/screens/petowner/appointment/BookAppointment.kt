package pe.edu.upc.upet.ui.screens.petowner.appointment

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pe.edu.upc.upet.feature_vet.data.remote.AvailableTimesRequest
import pe.edu.upc.upet.feature_vet.data.repository.VetRepository
import pe.edu.upc.upet.navigation.Routes
import pe.edu.upc.upet.ui.shared.CustomButton
import pe.edu.upc.upet.ui.shared.TextSubtitle2
import pe.edu.upc.upet.ui.shared.TopBar
import pe.edu.upc.upet.ui.theme.Blue1
import pe.edu.upc.upet.ui.theme.BorderPadding
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookAppointment(navController: NavController, vetId: Int) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedTime by remember {
        mutableStateOf(
            LocalTime.of(
                LocalTime.now().hour,
                (LocalTime.now().minute / 30) * 30
            )
        )
    }
    var currentYearMonth by remember { mutableStateOf(YearMonth.now()) }
    var availableTimes by remember { mutableStateOf<List<String>>(emptyList()) }
    val vetRepository = VetRepository()

    LaunchedEffect(key1 = selectedDate) {
        vetRepository.getAvailableTimes(vetId, AvailableTimesRequest(selectedDate.toString())) { times ->
            Log.d("AvailableTimes", selectedDate.toString())
            Log.d("AvailableTimes", times.toString())
            availableTimes = times?.toSet()?.toList() ?: emptyList()
        }
    }

    Scaffold(topBar = {
        TopBar(navController = navController, title = "Book Appointment")
    }, modifier = Modifier) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                item {
                    Column(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                            .padding(top = 10.dp, start = BorderPadding, end = BorderPadding),
                        verticalArrangement = Arrangement.Top
                    ) {
                        TextSubtitle2("Select Date")

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xA9F1DADC), RoundedCornerShape(15.dp))
                                .padding(8.dp)
                        ) {
                            Column {
                                MonthPicker(
                                    currentYearMonth = currentYearMonth,
                                    onYearMonthChange = { newYearMonth ->
                                        currentYearMonth = newYearMonth
                                    }
                                )

                                CalendarView(
                                    currentYearMonth = currentYearMonth,
                                    selectedDate = selectedDate,
                                    onDateSelected = { date ->
                                        selectedDate = date
                                    }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(25.dp))

                        TextSubtitle2("Select Available Hour")

                        TimePickerView(
                            availableTimes = availableTimes.map { LocalTime.parse(it, DateTimeFormatter.ofPattern("HH:mm:ss")).toString() },
                            selectedTime = selectedTime,
                            onTimeSelected = { time ->
                                selectedTime = time
                            }
                        )
                        Spacer(modifier = Modifier.height(25.dp))

                        CustomButton(text = "Next", onClick = {
                            navController.navigate(Routes.PetDetailsAppointment.createRoute(vetId, selectedDate.toString(), selectedTime.toString()))
                        })
                        Spacer(modifier = Modifier.height(20.dp))
                    }

                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthPicker(currentYearMonth: YearMonth, onYearMonthChange: (YearMonth) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = {
            onYearMonthChange(currentYearMonth.minusMonths(1))
        }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBackIos, contentDescription = "Previous Month")
        }

        Text(
            text = currentYearMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
        )

        IconButton(onClick = {
            onYearMonthChange(currentYearMonth.plusMonths(1))
        }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = "Next Month")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarView(currentYearMonth: YearMonth, selectedDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    val today = LocalDate.now()
    val daysInMonth = currentYearMonth.lengthOfMonth()
    val firstDayOfMonth = currentYearMonth.atDay(1).dayOfWeek.value % 7

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        for (week in 0 until 6) {
            Row {
                for (day in 0 until 7) {
                    val dayOfMonth = week * 7 + day - firstDayOfMonth + 1
                    if (dayOfMonth in 1..daysInMonth) {
                        val date = currentYearMonth.atDay(dayOfMonth)
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(4.dp)
                                .border(
                                    width = 2.dp,
                                    color = if (date == today) Blue1 else Color.Transparent,
                                    shape = CircleShape,
                                )
                                .background(
                                    if (selectedDate == date) Blue1 else Color.Transparent,
                                    RoundedCornerShape(50)
                                )
                                .clickable {
                                    onDateSelected(date)
                                }
                        ) {
                            Text(
                                text = dayOfMonth.toString(),
                                color = when {
                                    selectedDate == date -> Color.White
                                    else -> Color.Gray
                                },
                                modifier = Modifier.align(Alignment.Center),
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimePickerView(availableTimes: List<String>?, selectedTime: LocalTime?, onTimeSelected: (LocalTime) -> Unit) {
    if (availableTimes.isNullOrEmpty()) {
        Text("No available times")
    } else {
        Log.d("availableTimes", availableTimes.toString())
        val times = remember {
            availableTimes.map {
                LocalTime.parse(it, DateTimeFormatter.ofPattern("HH:mm")) // Change the pattern here
            }
        }

        val rows = times.chunked(4)

        Column {
            rows.forEach { rowTimes ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    rowTimes.forEach { time ->
                        val isSelected = selectedTime == time
                        val backgroundColor = if (isSelected) Color(0xFFFF8F86) else Color.White
                        val textColor = if (isSelected) Color.White else Color.Gray
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .height(75.dp)
                                .padding(4.dp)
                                .background(backgroundColor, RoundedCornerShape(15.dp))
                                .clickable {
                                    onTimeSelected(time)
                                }
                                .wrapContentSize(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = time.format(DateTimeFormatter.ofPattern("hh:mm")),
                                color = textColor,
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = time.format(DateTimeFormatter.ofPattern("a")),
                                color = textColor,
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold),
                            )
                        }
                    }
                    repeat(4 - rowTimes.size) {
                        Spacer(modifier = Modifier
                            .weight(1f)
                            .height(75.dp))
                    }
                }
            }
        }
    }
}
