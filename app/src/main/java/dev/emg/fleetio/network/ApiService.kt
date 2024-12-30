package dev.emg.fleetio.network

import dev.emg.fleetio.network.models.FilterBy
import dev.emg.fleetio.network.models.SortBy
import dev.emg.fleetio.network.models.VehicleRecordResponse
import dev.emg.fleetio.network.models.VehiclesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {

    @GET("/api/v1/vehicles")
    suspend fun latestApiVehicles(
        @Query("per_page") perPage: Int = PAGE_SIZE,
        @Query("start_cursor") startCursor: String = "",
        @QueryMap sortBy: Map<String, String> = SortBy.Name.queryParam,
        @QueryMap filterBy: Map<String, String> = FilterBy.Default.queryParam
    ): VehiclesResponse

    @GET("/api/v1/vehicles")
    suspend fun vehicles(
        @Query("per") per: Int = PAGE_SIZE,
        @Query("page") page: Int = 1,
        @QueryMap sortBy: Map<String, String> = SortBy.Name.queryParam,
        @QueryMap filterBy: Map<String, String> = FilterBy.Default.queryParam
    ): List<VehicleRecordResponse>

    @GET("/api/v1/vehicles/{id}")
    suspend fun vehicleById(
        @Path("id") id: String?
    ): VehicleRecordResponse

    companion object {
        const val BASE_URL = "https://secure.fleetio.com"
        const val PAGE_SIZE = 50
    }
}
