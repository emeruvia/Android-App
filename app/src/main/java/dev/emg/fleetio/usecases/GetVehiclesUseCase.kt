package dev.emg.fleetio.usecases

import androidx.paging.PagingData
import dev.emg.fleetio.network.models.FilterBy
import dev.emg.fleetio.network.models.SortBy
import dev.emg.fleetio.repository.VehicleRepository
import dev.emg.fleetio.repository.models.Vehicle
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVehiclesUseCase @Inject constructor(
    private val vehicleRepository: VehicleRepository
) {
    operator fun invoke(
        sortBy: SortBy,
        filterBy: FilterBy
    ): Flow<PagingData<Vehicle>> {
        return vehicleRepository.getVehicles(
            sortBy = sortBy,
            filterBy = filterBy
        )
    }
}