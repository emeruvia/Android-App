package dev.emg.fleetio.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dev.emg.fleetio.repository.models.Vehicle
import dev.emg.fleetio.repository.models.VehicleStatus
import dev.emg.fleetio.repository.models.VehicleType

@JsonClass(generateAdapter = true)
data class VehiclesResponse(
    @Json(name = "start_cursor") val startCursor: String,
    @Json(name = "next_cursor") val nextCursor: String?,
    @Json(name = "per_page") val perPage: Int,
    @Json(name = "estimated_remaining_count") val estimatedRemainingCount: Int,
    val records: List<VehicleRecordResponse>
)

@JsonClass(generateAdapter = true)
data class VehicleRecordResponse(
    val id: Int,
    @Json(name = "account_id") val accountId: Int,
    @Json(name = "fuel_volume_units") val fuelVolumeUnits: String?,
    val name: String,
    val ownership: String,
    @Json(name = "system_of_measurement") val systemOfMeasurement: String?,
    @Json(name = "vehicle_type_id") val vehicleTypeId: Int,
    @Json(name = "vehicle_type_name") val vehicleTypeName: String,
    @Json(name = "vehicle_status_id") val vehicleStatusId: Int,
    @Json(name = "vehicle_status_name") val vehicleStatusName: String,
    @Json(name = "vehicle_status_color") val vehicleStatusColor: String,
    @Json(name = "comments_count") val commentsCount: Int,
    @Json(name = "default_image_url_small") val defaultImageUrlSmall: String?,
    @Json(name = "created_at") val createdAt: String,
    @Json(name = "updated_at") val updatedAt: String,
    val vin: String?,
    val year: Int?
)

fun List<VehicleRecordResponse>.toVehicleList(): List<Vehicle> {
    return map { it.toVehicle() }
}

fun VehicleRecordResponse.toVehicle(): Vehicle {
    return Vehicle(
        id = id,
        accountId = accountId,
        name = name,
        fuelVolumeUnits = fuelVolumeUnits,
        ownership = ownership,
        systemOfMeasurement = systemOfMeasurement,
        commentsCount = commentsCount,
        type = VehicleType(id = vehicleTypeId, name = vehicleTypeName),
        status = VehicleStatus(
            id = vehicleStatusId,
            name = vehicleStatusName,
            color = vehicleStatusColor
        ),
        vin = vin,
        year = year,
        defaultImageUlr = defaultImageUrlSmall
    )
}