package dev.emg.fleetio

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.emg.fleetio.ui.details.VehicleDetailsScreen
import dev.emg.fleetio.ui.vehicles.VehiclesScreen
import kotlinx.serialization.Serializable

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController()
) {

    NavHost(
        navController = navHostController,
        startDestination = Home,
        modifier = modifier
    ) {
        composable<Home> {
            VehiclesScreen(
                onVehicleDetailsClick = { vehicleId ->
                    navHostController.navigate(Details(vehicleId))
                }
            )
        }
        composable<Details> { backStackEntry ->
            val vehicleId = backStackEntry.toRoute<Details>().id
            VehicleDetailsScreen(
                onBack = {
                    navHostController.popBackStack()
                },
                vehicleId = vehicleId
            )
        }
    }
}

@Serializable
object Home
@Serializable
data class Details(val id: Int)