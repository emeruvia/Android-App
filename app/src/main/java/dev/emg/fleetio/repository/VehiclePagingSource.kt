package dev.emg.fleetio.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.emg.fleetio.network.ApiService
import dev.emg.fleetio.network.ApiService.Companion.PAGE_SIZE
import dev.emg.fleetio.network.models.FilterBy
import dev.emg.fleetio.network.models.SortBy
import dev.emg.fleetio.network.models.toVehicleList
import dev.emg.fleetio.repository.models.Vehicle
import timber.log.Timber

/**
 * Unlike the newer api, this API endpoint doesn't rely on start_cursor for getting
 * the next set page of API.
 * Upon looking how the vehicle API is implement in the web app, I figured out that the
 * paging values needed are:
 * - per: how many items to loaad
 * - page: page int
 */
class VehiclePagingSource(
    private val apiService: ApiService,
    private val sortBy: SortBy,
    private val filterBy: FilterBy
) : PagingSource<Int, Vehicle>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Vehicle> {
        return try {
            val nextPage: Int = params.key ?: 1
            val vehicles = apiService.vehicles(
                per = PAGE_SIZE,
                page = nextPage,
                sortBy = sortBy.queryParam,
                filterBy = filterBy.queryParam
            )
            val isLastPage = vehicles.size < PAGE_SIZE
            LoadResult.Page(
                data = vehicles.toVehicleList(),
                prevKey = null,
                nextKey = if (isLastPage) null else nextPage + 1
            )
        } catch (exception: Exception) {
            Timber.e(exception)
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Vehicle>): Int? {
        return 1
    }
}