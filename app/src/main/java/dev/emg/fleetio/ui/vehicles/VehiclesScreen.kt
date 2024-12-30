package dev.emg.fleetio.ui.vehicles

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import dev.emg.fleetio.AppTheme
import dev.emg.fleetio.repository.models.Vehicle
import dev.emg.fleetio.repository.models.VehicleStatus
import dev.emg.fleetio.repository.models.VehicleType
import dev.emg.fleetio.repository.models.toHeader
import dev.emg.fleetio.ui.components.ErrorComponent
import dev.emg.fleetio.ui.components.HomeAppBarComponent
import dev.emg.fleetio.ui.components.LoadingComponent
import dev.emg.fleetio.ui.components.StatusComponent
import dev.emg.fleetio.ui.components.VehicleComponent
import timber.log.Timber

@Composable
fun VehiclesScreen(
    modifier: Modifier = Modifier,
    onVehicleDetailsClick: (Int) -> Unit
) {
    val viewModel: VehiclesViewModel = hiltViewModel()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            HomeAppBarComponent(modifier = modifier, viewModel = viewModel)
        }
    ) { innerPadding ->
        val vehiclePagingItems = viewModel.vehiclePagingList.collectAsLazyPagingItems()
        val loadState = vehiclePagingItems.loadState

        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            when (loadState.refresh) {
                is LoadState.Loading -> {
                    LoadingComponent()
                }

                is LoadState.Error -> {
                    val error = (loadState.refresh as LoadState.Error).error
                    ErrorComponent(errorMsg = "Error: ${error.message}")
                }

                is LoadState.NotLoading -> {
                    Timber.d("nothing to load")
                }
            }

            LazyColumn {
                items(vehiclePagingItems.itemCount) { index ->
                    vehiclePagingItems[index]?.let { vehicle ->
                        VehicleItem(
                            vehicle = vehicle,
                            onVehicleDetailsClick = {
                                onVehicleDetailsClick(vehicle.id)
                            }
                        )
                    }
                }
                item {
                    if (loadState.append is LoadState.Loading) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun VehicleItem(
    vehicle: Vehicle,
    onVehicleDetailsClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .padding(start = 12.dp, end = 12.dp, top = 4.dp, bottom = 4.dp)
            .clickable {
                Timber.d("Clicked on vehicle $vehicle")
                onVehicleDetailsClick()
            }
    ) {
        Column {
            VehicleComponent(vehicle)
            Row(
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text(text = vehicle.toHeader(), fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(weight = 1f))
                StatusComponent(
                    vehicleStatus = vehicle.status,
                    modifier = Modifier.align(Alignment.Bottom)
                )
            }
            val description = buildAnnotatedString {
                append("${vehicle.type.name} ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("•")
                }
                append(" ${vehicle.ownership}")
            }
            Text(text = description, fontSize = 14.sp)

        }
    }
}

@Preview
@Composable
private fun VehicleItemPreview() {
    val previewItem = Vehicle(
        id = 12345,
        accountId = 123456,
        name = "Vehicle-001",
        fuelVolumeUnits = "mi",
        ownership = "Owned",
        systemOfMeasurement = "imperial",
        commentsCount = 0,
        type = VehicleType(id = 1, name = "Car"),
        status = VehicleStatus(id = 1, name = "Active", color = "green"),
        vin = "8675309",
        year = 2024,
        defaultImageUlr = null
    )
    AppTheme {
        Surface {
            VehicleItem(previewItem) {
                //Preview clicked
            }
        }
    }
}