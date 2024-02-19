package com.ayush.a1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Stop(val name: String, val distanceInKm: Float, val distanceInMiles: Float)

val dummyStops = listOf(
    Stop("Stop 1", 5f, 3.1f),
    Stop("Stop 2", 8f, 4.97f),
    Stop("Stop 3", 11f,6.83f),
    Stop("Stop 4", 14f,8.69f),
    Stop("Stop 5", 17f,10.56f),
   /* Stop("Stop 6", 20f,12.42f),
    Stop("Stop 7", 23f,14.29f),
    Stop("Stop 8", 26f,16.15f),
    Stop("Stop 9", 29f,18.01f),
    Stop("Stop 10",32f,19.88f),
    Stop("Stop 11",35f,21.74f),*/
    // Add more stops as needed
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun JourneyApp() {
    var currentStopIndex by remember { mutableStateOf(0) }
    var isKmUnit by remember { mutableStateOf(true) }
    var totalDistanceCovered by remember { mutableStateOf(0f) }

    var progressList by remember { mutableStateOf(dummyStops.take(2)) }

    val currentStop = progressList[currentStopIndex]

    var totalDistanceLeft by remember { mutableStateOf(dummyStops.sumByDouble { if (isKmUnit) it.distanceInKm.toDouble() else it.distanceInMiles.toDouble() }.toFloat()) }

    var progressState by remember { mutableStateOf(0f) }

    var keyboardController by remember { mutableStateOf<SoftwareKeyboardController?>(null) }

    var snackbarVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Journey Details", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Button(onClick = { isKmUnit = !isKmUnit }) {
                Text(if (isKmUnit) "Switch to Miles" else "Switch to Km")
            }
        }

        // Progress Section
        ProgressSection(
            currentStop = currentStop,
            totalDistanceCovered = totalDistanceCovered,
            totalDistanceLeft = totalDistanceLeft,
            progressState = progressState,
            isKmUnit = isKmUnit
        )

        // Lazy List for Stops
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(dummyStops.size) { index ->
                StopItem(dummyStops[index])
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Buttons Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                totalDistanceCovered += if (isKmUnit) currentStop.distanceInKm else currentStop.distanceInMiles
                totalDistanceLeft -= if (isKmUnit) currentStop.distanceInKm else currentStop.distanceInMiles
                progressState = (totalDistanceCovered / dummyStops.sumByDouble { if (isKmUnit) it.distanceInKm.toDouble() else it.distanceInMiles.toDouble() }).toFloat()

                if (currentStopIndex < dummyStops.size - 1) {
                    currentStopIndex++
                    progressList = dummyStops.take(currentStopIndex + 1)
                } else {
                    // Journey completed
                    keyboardController?.hide()
                    snackbarVisible = true
                }
            }) {
                Text("Next Stop")
            }

            Button(onClick = {
                keyboardController?.hide()
                snackbarVisible = true
            }){
                Text(text = "Stop reached!")
            }

        }
    }
}

@Composable
fun StopItem(stop: Stop) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text("Stop: ${stop.name}")
            Text("Distance: ${stop.distanceInKm} km / ${stop.distanceInMiles} miles")
        }
    }
}

@Composable
fun ProgressSection(
    currentStop: Stop,
    totalDistanceCovered: Float,
    totalDistanceLeft: Float,
    progressState: Float,
    isKmUnit: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Progress Bar
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp),
            progress = progressState,
            color = MaterialTheme.colorScheme.primary
        )

        // Progress Details
        Spacer(modifier = Modifier.height(8.dp))
        Text("Current Stop: ${currentStop.name}")
        Spacer(modifier = Modifier.height(4.dp))
        Text("Distance to Next Stop: ${currentStop.distanceInKm} km / ${currentStop.distanceInMiles} miles")
        Spacer(modifier = Modifier.height(4.dp))
        Text("Total Distance Covered: ${totalDistanceCovered} ${if (isKmUnit) "km" else "miles"}")
        Spacer(modifier = Modifier.height(4.dp))
        Text("Total Distance Left: ${totalDistanceLeft} ${if (isKmUnit) "km" else "miles"}")
    }
}

@Composable
fun MyApp() {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        JourneyApp()
    }
}

@Preview
@Composable
fun watchPreview(){
    MyApp()
}
