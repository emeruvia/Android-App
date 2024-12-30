package dev.emg.fleetio.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.emg.fleetio.network.ApiService
import dev.emg.fleetio.network.ApiService.Companion.PAGE_SIZE
import dev.emg.fleetio.network.ApiState
import dev.emg.fleetio.network.models.FilterBy
import dev.emg.fleetio.network.models.SortBy
import dev.emg.fleetio.network.models.toVehicle
import dev.emg.fleetio.repository.models.Vehicle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VehicleRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : VehicleRepository {

    override fun getVehicles(
        sortBy: SortBy,
        filterBy: FilterBy
    ): Flow<PagingData<Vehicle>> {
        val pagingSourceFactory = VehiclePagingSource(apiService, sortBy, filterBy)
        val pagingConfig = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            prefetchDistance = 10
        )
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                pagingSourceFactory
            }
        ).flow
    }

    override fun getVehicleById(id: String?) = flow<ApiState<Vehicle>> {
        val response = apiService.vehicleById(id)
        emit(ApiState.Success(response.toVehicle()))
    }.onStart {
        emit(ApiState.Loading)
    }.catch { throwable ->
        emit(ApiState.Error(throwable, throwable.message))
    }
}