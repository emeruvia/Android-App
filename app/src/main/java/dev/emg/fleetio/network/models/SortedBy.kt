package dev.emg.fleetio.network.models

/**
 * There is a bug when trying to append a + sign for asc and desc values.
 * For now it will default to sort in ascending order
 */
sealed class SortBy(val queryParam: Map<String, String>) {
    data object Name : SortBy(queryParam = mapOf(sortQuery to "name"))
    data object CreatedAt : SortBy(queryParam = mapOf(sortQuery to "created_at"))
    data object UpdatedAt : SortBy(queryParam = mapOf(sortQuery to "updated_at"))
    data object Type : SortBy(queryParam = mapOf(sortQuery to "vehicle_type_name"))
    data object Status : SortBy(queryParam = mapOf(sortQuery to "vehicle_status_name"))
}

const val sortQuery = "q[s]"

fun sortByOptions(): List<Pair<SortBy, String>> {
    return listOf(
        Pair(SortBy.Name, "Name"),
        Pair(SortBy.CreatedAt, "Created at"),
        Pair(SortBy.UpdatedAt, "Updated at"),
        Pair(SortBy.Type, "Type"),
        Pair(SortBy.Status, "Status"),
    )
}