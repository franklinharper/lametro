package com.franklinharper.kickstart

import PermissionManager
import android.os.Bundle
import com.google.android.gms.location.LocationServices
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : BaseActivity() {

    lateinit var presenter: MainPresenter

    // In production code these dependencies would be injected
    private val permissionManager = PermissionManager(this)
    private val loggingInterceptor = HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BODY }
    private val clientBuilder = OkHttpClient.Builder().also { it.addInterceptor(loggingInterceptor) }

    // Retrofit.build() returns a Platform Type (Java code).
    // Declaring an explicit type lets Kotlin know if the `retrotfit` property is nullable or not.
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.metro.net/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(clientBuilder.build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val laMetroApi: LaMetroApi = retrofit.create(LaMetroApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ui = MainUiImpl(this)
        presenter = MainPresenterImpl(
            ui,
            laMetroApi,
            permissionManager,
            LocationServices.getFusedLocationProviderClient(this)
        )
        ui.onCreate(presenter)
        presenter.onCreate(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        permissionManager.onRequestPermissionsResult(requestCode, grantResults)
    }

}
