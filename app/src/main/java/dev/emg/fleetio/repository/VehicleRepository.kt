package dev.emg.fleetio.repository

import androidx.paging.PagingData
import dev.emg.fleetio.network.ApiState
import dev.emg.fleetio.network.models.FilterBy
import dev.emg.fleetio.network.models.SortBy
import dev.emg.fleetio.repository.models.Vehicle
import kotlinx.coroutines.flow.Flow

interface VehicleRepository {

    fun getVehicles(
        sortBy: SortBy,
        filterBy: FilterBy
    ): Flow<PagingData<Vehicle>>

    fun getVehicleById(id: String?): Flow<ApiState<Vehicle>>
}