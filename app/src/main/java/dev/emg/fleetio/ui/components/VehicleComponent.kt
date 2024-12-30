package dev.emg.fleetio.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.emg.fleetio.R
import dev.emg.fleetio.repository.models.Vehicle

@Composable
fun VehicleComponent(
    vehicle: Vehicle,
    roundedCorners: Boolean = true
) {
    val roundedCornerSize = if (roundedCorners) 8.dp else 0.dp
    val imageModifier = Modifier
        .clip(RoundedCornerShape(roundedCornerSize))
        .background(color = MaterialTheme.colorScheme.tertiary)
        .fillMaxWidth()

    if (vehicle.defaultImageUlr.isNullOrBlank()) {
        val drawableToLoad = when (vehicle.type.name) {
            "Car" -> R.drawable.car
            "Pickup Truck" -> R.drawable.truck
            "Van" -> R.drawable.van
            else -> R.drawable.car
        }

        Image(
            painter = painterResource(id = drawableToLoad),
            contentDescription = vehicle.type.name,
            modifier = imageModifier,
            contentScale = ContentScale.Fit
        )
    } else {
        AsyncImage(
            model = vehicle.defaultImageUlr,
            contentDescription = null,
            modifier = imageModifier,
            contentScale = ContentScale.Crop
        )
    }
}