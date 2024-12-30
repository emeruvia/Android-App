package dev.emg.fleetio.repository

import androidx.paging.PagingData
import dev.emg.fleetio.network.ApiState
import dev.emg.fleetio.network.models.FilterBy
import dev.emg.fleetio.network.models.SortBy
import dev.emg.fleetio.repository.models.Vehicle
import dev.emg.fleetio.repository.models.VehicleStatus
import dev.emg.fleetio.repository.models.VehicleType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeVehicleRepository() : VehicleRepository {
    val vehicle1 = Vehicle(
        id = 3739720,
        accountId = 433656,
        name = "Vehicle-002",
        fuelVolumeUnits = "us_gallons",
        ownership = "Owned",
        systemOfMeasurement = "imperial",
        commentsCount = 0,
        type = VehicleType(id = 1700884, name = "Car"),
        status = VehicleStatus(id = 645677, name = "In Shop", color = "orange"),
        vin = null,
        year = 2018,
        defaultImageUlr = null
    )
    val vehicle2 = Vehicle(
        id = 3739720,
        accountId = 433656,
        name = "Vehicle-100",
        fuelVolumeUnits = "us_gallons",
        ownership = "Owned",
        systemOfMeasurement = "imperial",
        commentsCount = 0,
        type = VehicleType(id = 1700891, name = "Pickup Truck"),
        status = VehicleStatus(id = 645676, name = "Active", color = "green"),
        vin = null,
        year = 2024,
        defaultImageUlr = null
    )
    val exception = Exception("Not found")
    private val vehicleList = listOf(vehicle1, vehicle2)

    override fun getVehicles(sortBy: SortBy, filterBy: FilterBy) = flow<PagingData<Vehicle>> {
        emit(PagingData.from(vehicleList))
    }

    override fun getVehicleById(id: String?) = flow<ApiState<Vehicle>> {
        emit(ApiState.Loading)
        val vehicle = vehicleList.find { it.id.toString() == id }
        vehicle?.let {
            emit(ApiState.Success(data = vehicle))
        } ?: run {
            emit(ApiState.Error(exception, exception.message))
        }
    }

}