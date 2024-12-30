package dev.emg.fleetio.ui.details

import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.emg.fleetio.repository.models.toHeader
import dev.emg.fleetio.ui.UiState
import dev.emg.fleetio.ui.components.ErrorComponent
import dev.emg.fleetio.ui.components.LoadingComponent
import dev.emg.fleetio.ui.components.StatusComponent
import dev.emg.fleetio.ui.components.VehicleComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleDetailsScreen(
    modifier: Modifier = Modifier,
    vehicleId: Int?,
    onBack: () -> Unit
) {
    val viewModel: VehicleDetailsViewModel = hiltViewModel()
    viewModel.getVehicleById(vehicleId.toString())

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Vehicle Details", fontSize = 26.sp)
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back button"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { innerPadding ->
        val vehicleState = viewModel.vehicleStateFlow.collectAsState().value

        Box(
            modifier = modifier
                .padding(innerPadding)
        ) {
            when (vehicleState) {
                is UiState.Loading -> {
                    LoadingComponent()
                }

                is UiState.Error -> {
                    ErrorComponent(errorMsg = vehicleState.msg)
                }

                is UiState.Success -> {
                    val vehicle = vehicleState.data
                    Column(
                      modifier = Modifier
                          .verticalScroll(rememberScrollState())
                    ) {
                        VehicleComponent(vehicle = vehicle, roundedCorners = false)
                        Column(
                            modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = vehicle.toHeader(),
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold
                            )
                            StatusComponent(vehicleStatus = vehicleState.data.status)
                            Text(text = vehicle.ownership, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text(text = "type: ${vehicle.type.name}", fontSize = 18.sp)
                            Text(text = "vin: ${vehicle.vin}", fontSize = 18.sp)
                            Text(text = "fuel volume units: ${vehicle.fuelVolumeUnits}", fontSize = 18.sp)
                            Text(text = "comments: ${vehicle.commentsCount}", fontSize = 18.sp)
                        }
                    }
                }
            }
        }
    }
}