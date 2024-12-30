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
 * In the latest API version, the filterBy and sortBy logic has been updated to only
 * receive the following values:
 * - name
 * - vin
 * - license_plate
 * - labels
 * - created_at
 * - updated_at
 *
 * In order to demonstrate the the filtering logic I'm re-implementing the older API source, keeping
 * this file here for possible future iterations
 */
class LatestApiVehiclePagingSource(
    private val apiService: ApiService,
    private val sortBy: SortBy,
    private val filterBy: FilterBy
) : PagingSource<String, Vehicle>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Vehicle> {
        return try {
            val nextCursor = params.key ?: ""

            val vehicles = apiService.latestApiVehicles(
                startCursor = nextCursor,
                perPage = PAGE_SIZE,
                sortBy = sortBy.queryParam,
                filterBy = filterBy.queryParam
            )
            LoadResult.Page(
                data = vehicles.records.toVehicleList(),
                prevKey = null,
                nextKey = vehicles.nextCursor
            )
        } catch (exception: Exception) {
            Timber.e(exception)
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Vehicle>): String? {
        return null
    }
}