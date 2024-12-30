package dev.emg.fleetio.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.emg.fleetio.network.ApiState
import dev.emg.fleetio.repository.models.Vehicle
import dev.emg.fleetio.ui.UiState
import dev.emg.fleetio.ui.UiState.Success
import dev.emg.fleetio.ui.UiState.Loading
import dev.emg.fleetio.ui.UiState.Error
import dev.emg.fleetio.usecases.GetVehicleByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleDetailsViewModel @Inject constructor(
    private val getVehicleByIdUseCase: GetVehicleByIdUseCase
) : ViewModel() {

    private val _vehicleStateFlow = MutableStateFlow<UiState<Vehicle>>(Loading)
    val vehicleStateFlow: StateFlow<UiState<Vehicle>>
        get() = _vehicleStateFlow

    fun getVehicleById(id: String?) {
        viewModelScope.launch {
            getVehicleByIdUseCase(id).collect { state ->
                when (state) {
                    is ApiState.Success -> {
                        _vehicleStateFlow.value = Success(data = state.data)
                    }

                    is ApiState.Loading -> {
                        _vehicleStateFlow.value = Loading
                    }

                    is ApiState.Error -> {
                        _vehicleStateFlow.value = Error(
                            msg = state.msg ?: "Uh oh! Something went wrong. Try again later."
                        )
                    }
                }
            }
        }
    }
}
