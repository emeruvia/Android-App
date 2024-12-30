package dev.emg.fleetio.usecases

import dev.emg.fleetio.MainCoroutineRule
import dev.emg.fleetio.repository.FakeVehicleRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

class GetVehiclesUseCaseTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    private val vehicleRepository = FakeVehicleRepository()

    private val useCase = GetVehicleByIdUseCase(
        vehicleRepository = vehicleRepository
    )


}