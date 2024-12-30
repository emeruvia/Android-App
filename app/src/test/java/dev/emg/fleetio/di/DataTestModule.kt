package dev.emg.fleetio.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import dev.emg.fleetio.repository.FakeVehicleRepository
import dev.emg.fleetio.repository.VehicleRepository
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
abstract class DataTestModule {
    @Singleton
    @Binds
    abstract fun bindsFakeRepository(fakeVehicleRepository: FakeVehicleRepository): VehicleRepository
}