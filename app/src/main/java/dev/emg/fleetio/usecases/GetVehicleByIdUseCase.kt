package dev.emg.fleetio.usecases

import dev.emg.fleetio.network.ApiState
import dev.emg.fleetio.repository.VehicleRepository
import dev.emg.fleetio.repository.models.Vehicle
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVehicleByIdUseCase @Inject constructor(
    private val vehicleRepository: VehicleRepository
) {
    operator fun invoke(
        id: String?
    ): Flow<ApiState<Vehicle>> {
        return vehicleRepository.getVehicleById(id = id)
    }
}