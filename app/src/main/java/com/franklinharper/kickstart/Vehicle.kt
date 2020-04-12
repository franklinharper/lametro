package com.franklinharper.kickstart

import com.google.gson.annotations.SerializedName

data class Vehicles(
    @SerializedName("items") val vehicles: List<Vehicle>
)

data class Vehicle(
    // Prevent bugs caused by renaming properties by always setting the SerializedName explicitly
    @SerializedName("id") val id: String,
    @SerializedName("route_id") val routeId: String,
    @SerializedName("run_id") val runId: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("heading") val heading: String,
    @SerializedName("seconds_since_report") val secondsSinceReport: String,
    @SerializedName("predictable") val predictable: String
)
