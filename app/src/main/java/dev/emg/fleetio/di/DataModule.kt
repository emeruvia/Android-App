package dev.emg.fleetio.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.emg.fleetio.repository.VehicleRepository
import dev.emg.fleetio.repository.VehicleRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindRepository(vehicleRepositoryImpl: VehicleRepositoryImpl): VehicleRepository

}