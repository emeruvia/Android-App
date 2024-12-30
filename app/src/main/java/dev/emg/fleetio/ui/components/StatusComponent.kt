package dev.emg.fleetio.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.emg.fleetio.R
import dev.emg.fleetio.repository.models.VehicleStatus


@Composable
fun StatusComponent(
    vehicleStatus: VehicleStatus,
    modifier: Modifier = Modifier
) {
    val statusColor = when (vehicleStatus.color.lowercase()) {
        "green" -> R.color.green
        "red" -> R.color.red
        "orange" -> R.color.orange
        else -> R.color.gray
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(18.dp)
                .padding(4.dp)
                .background(
                    color = colorResource(statusColor),
                    shape = androidx.compose.foundation.shape.CircleShape
                )
        )

        Text(
            text = vehicleStatus.name,
            fontSize = 18.sp
        )
    }
}