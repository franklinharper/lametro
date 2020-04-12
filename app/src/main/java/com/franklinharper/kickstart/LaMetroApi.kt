package com.franklinharper.kickstart

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface LaMetroApi {
    @GET("agencies/{agency}/vehicles/")
    fun getVehicles(@Path("agency") agencyId: String): Observable<Vehicles>
}
