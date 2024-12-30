package dev.emg.fleetio.network

import dev.emg.fleetio.network.models.VehicleRecordResponse
import dev.emg.fleetio.network.models.VehiclesResponse

class FakeApiService(
    val vehicleList: MutableList<VehicleRecordResponse> = mutableListOf()
) : ApiService {
    override suspend fun latestApiVehicles(
        perPage: Int,
        startCursor: String,
        sortBy: Map<String, String>,
        filterBy: Map<String, String>
    ): VehiclesResponse {
        TODO("Not yet implemented")
    }

    override suspend fun vehicles(
        per: Int,
        page: Int,
        sortBy: Map<String, String>,
        filterBy: Map<String, String>
    ): List<VehicleRecordResponse> {
        return vehicleList.toList()
    }

    override suspend fun vehicleById(id: String?): VehicleRecordResponse {
        return vehicleList.first()
    }

}