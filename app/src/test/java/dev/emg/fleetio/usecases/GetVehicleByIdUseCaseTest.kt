package dev.emg.fleetio.usecases

import dev.emg.fleetio.MainCoroutineRule
import dev.emg.fleetio.network.ApiState
import dev.emg.fleetio.repository.FakeVehicleRepository
import dev.emg.fleetio.repository.models.Vehicle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals


class GetVehicleByIdUseCaseTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    private val vehicleRepository = FakeVehicleRepository()

    val useCase = GetVehicleByIdUseCase(
        vehicleRepository = vehicleRepository
    )

    @Test
    fun testFetchVehicleByIdIsSuccess() = runTest {
        val useCase = useCase(id = "3739720")
        val actual = mutableListOf<ApiState<Vehicle>>()

        useCase.collect {
            actual.add(it)
        }

        assertEquals(
            expected = listOf(
                ApiState.Loading,
                ApiState.Success(vehicleRepository.vehicle1)
            ),
            actual = actual.toList()
        )
    }

    @Test
    fun testFetchVehicleByIdErrorIsReturned() = runTest {
        val useCase = useCase(id = "1")
        val actual = mutableListOf<ApiState<Vehicle>>()

        useCase.collect {
            actual.add(it)
        }

        assertEquals(
            expected = listOf(
                ApiState.Loading,
                ApiState.Error(vehicleRepository.exception, vehicleRepository.exception.message)
            ),
            actual = actual.toList()
        )
    }

}