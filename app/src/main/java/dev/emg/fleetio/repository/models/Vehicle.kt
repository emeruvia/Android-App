package dev.emg.fleetio.repository.models

data class Vehicle(
    val id: Int,
    val accountId: Int,
    val name: String,
    val fuelVolumeUnits: String?,
    val ownership: String,
    val systemOfMeasurement: String?,
    val commentsCount: Int,
    val type: VehicleType,
    val status: VehicleStatus,
    val vin: String?,
    val year: Int?,
    val defaultImageUlr: String?
)

data class VehicleType(
    val id: Int,
    val name: String
)

data class VehicleStatus(
    val id: Int,
    val name: String,
    val color: String
)

// Returns: 2024 Vehicle-001
fun Vehicle.toHeader(): String {
    val vehicleYear = if (year == null) "" else "$year "
    return "$vehicleYear$name"
}

