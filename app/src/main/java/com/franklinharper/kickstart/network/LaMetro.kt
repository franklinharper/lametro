package com.franklinharper.kickstart.network

import retrofit2.http.GET
import com.google.gson.annotations.SerializedName
import io.reactivex.Single

interface LaMetroApi {
    @GET("agencies/")
    fun listAgencies() : Single<List<Agency>>
}

data class Agency(
    @SerializedName("display_name")
    val name: String,

    @SerializedName("id")
    val id: String
)
