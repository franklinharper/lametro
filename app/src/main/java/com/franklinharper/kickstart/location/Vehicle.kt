package com.franklinharper.kickstart.location

import com.google.gson.annotations.SerializedName

data class VehicleLocations(
    @SerializedName("items") val vehicles: List<Vehicle> = emptyList()
)

data class Vehicle(
    // Always set the SerializedName explicitly, this prevents bugs caused when renaming properties
    @SerializedName("id") val vehicleId: String,
    @SerializedName("route_id") val routeId: String,
    @SerializedName("run_id") val runId: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("heading") val heading: Float,
    @SerializedName("seconds_since_report") val secondsSinceReport: String,
    @SerializedName("predictable") val predictable: String
)
