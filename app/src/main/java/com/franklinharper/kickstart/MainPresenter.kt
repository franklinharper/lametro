package com.franklinharper.kickstart

import android.content.Intent
import android.location.Location
import androidx.annotation.VisibleForTesting
import com.franklinharper.kickstart.recyclerview.RecyclerViewItem
import com.google.android.gms.location.FusedLocationProviderClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

enum class LocationOption {
    CURRENT_LOCATION, ENTER_LOCATION, REMOVE_LOCATION
}

interface MainPresenter {
//    fun onCreate(intent: Intent)
//    fun onDestroy()
//    fun onRetryClick()
//    fun onSetLocationClick()
//    fun locationOptionClick(which: LocationOption)
//    fun searchByZipcode(zipcode: String)
}


class MainPresenterImpl(
    private val ui: MainUi,
    private val laMetroApi: LaMetroApi,
    private val permissionManager: PermissionManager,
    private val fusedLocationClient: FusedLocationProviderClient
) : MainPresenter {

//    private val compositeDisposable = CompositeDisposable()
//    private var location: Location? = null
//    private var zipcode: String? = null
//
//    override fun onCreate(intent: Intent) {
//        search()
//    }
//
//    override fun onDestroy() {
//        compositeDisposable.clear()
//    }
//
//    override fun onRetryClick() {
//        search()
//    }
//
//    override fun onSetLocationClick() {
//        if (location == null && zipcode == null) {
//            ui.showLocationOptionsDialog(arrayOf("Current Location", "Enter Location"))
//        } else {
//            ui.showLocationOptionsDialog(
//                arrayOf(
//                    "Current Location",
//                    "Enter Location",
//                    "Remove Location"
//                )
//            )
//        }
//    }
//
//    override fun locationOptionClick(which: LocationOption) {
//        when (which) {
//            LocationOption.CURRENT_LOCATION -> {
//                currentLocationClick()
//            }
//            LocationOption.ENTER_LOCATION -> {
//                enterLocation()
//            }
//            LocationOption.REMOVE_LOCATION -> {
//                removeLocation()
//            }
//        }
//    }
//
//    override fun searchByZipcode(zipcode: String) {
//        this.zipcode = zipcode
//        location = null
//        search()
//    }
//
//    @VisibleForTesting
//    fun searchByLocation(location: Location) {
//        this.location = location
//        zipcode = null
//        search()
//    }
//
//    private fun enterLocation() {
//        ui.showEnterLocationDialog()
//    }
//
//    private fun removeLocation() {
//        location = null
//        zipcode = null
//        search()
//    }
//
//
//    private fun currentLocationClick() {
//        permissionManager.request(Permission.ACCESS_COARSE_LOCATION, object :
//            PermissionListener {
//
//            override fun onGranted(permission: Permission) {
//                fusedLocationClient.lastLocation
//                    .addOnSuccessListener { location ->
//                        if (location == null) {
//                            ui.showError(RecyclerViewItem.ErrorItem("Unable to retrieve current location"))
//                        } else {
//                            searchByLocation(location)
//                        }
//                    }
//            }
//
//            override fun onDenied(permission: Permission) {
//                ui.showLocationPermissionDenied()
//            }
//        })
//    }
//
}

