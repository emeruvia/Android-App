package dev.emg.fleetio.network.models

sealed class FilterBy(val queryParam: Map<String, String>) {
    data object Default : FilterBy(queryParam = mapOf("" to ""))
    data object StatusActive : FilterBy(queryParam = mapOf(QueryType.StatusEq.query to "Active"))
    data object StatusInShop : FilterBy(queryParam = mapOf(QueryType.StatusEq.query to "In Shop"))
    data object StatusOutOfService :
        FilterBy(queryParam = mapOf(QueryType.StatusEq.query to "Out of Service"))

    data object TypeCar : FilterBy(queryParam = mapOf(QueryType.VehicleEq.query to "Car"))
    data object TypeTruck :
        FilterBy(queryParam = mapOf(QueryType.VehicleEq.query to "Pickup Truck"))

    data object TypeVan : FilterBy(queryParam = mapOf(QueryType.VehicleEq.query to "Van"))
}

enum class QueryType(val query: String) {
    StatusEq(query = "q[vehicle_status_name_eq]"),
    VehicleEq(query = "q[vehicle_type_name_eq]")
}

fun filterByOptions(): List<Pair<FilterBy, String>> {
    return listOf(
        Pair(FilterBy.Default, "Default"),
        Pair(FilterBy.StatusActive, "Status: Active"),
        Pair(FilterBy.StatusInShop, "Status: In Shop"),
        Pair(FilterBy.StatusOutOfService, "Status: Out of Service"),
        Pair(FilterBy.TypeCar, "Type: Car"),
        Pair(FilterBy.TypeTruck, "Type: Truck"),
        Pair(FilterBy.TypeVan, "Type: Van"),
    )
}