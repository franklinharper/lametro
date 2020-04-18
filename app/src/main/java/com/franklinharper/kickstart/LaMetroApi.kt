package com.franklinharper.kickstart

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface LaMetroApi {
  @GET("agencies/{agency}/vehicles/")
  fun getVehicleLocations(@Path("agency") agencyId: String): Single<VehicleLocations>
}
