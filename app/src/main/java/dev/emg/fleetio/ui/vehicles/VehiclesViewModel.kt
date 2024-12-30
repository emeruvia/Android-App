package dev.emg.fleetio.ui.vehicles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.emg.fleetio.network.models.FilterBy
import dev.emg.fleetio.network.models.SortBy
import dev.emg.fleetio.repository.models.Vehicle
import dev.emg.fleetio.usecases.GetVehiclesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class VehiclesViewModel @Inject constructor(
    getVehicleUseCase: GetVehiclesUseCase
) : ViewModel() {

    private val _filterByState = MutableStateFlow<FilterBy>(FilterBy.Default)
    val filterByState: StateFlow<FilterBy> get() = _filterByState
    private val _sortByState = MutableStateFlow<SortBy>(SortBy.Name)
    val sortByState: StateFlow<SortBy> get() = _sortByState

    val vehiclePagingList: Flow<PagingData<Vehicle>> =
        combine(_filterByState, _sortByState) { filterBy, sortBy ->
            Pair(filterBy, sortBy)
        }.flatMapLatest { (filterBy, sortBy) ->
            getVehicleUseCase(filterBy = filterBy, sortBy = sortBy)
        }

    fun setFilterByOption(filterBy: FilterBy) {
        _filterByState.value = filterBy
    }

    fun setSortByOption(sortBy: SortBy) {
        _sortByState.value = sortBy
    }
}