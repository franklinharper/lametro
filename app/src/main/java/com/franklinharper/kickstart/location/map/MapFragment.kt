package com.franklinharper.kickstart.location.map

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.franklinharper.kickstart.App
import com.franklinharper.kickstart.R
import com.franklinharper.kickstart.databinding.MapFragmentBinding
import com.franklinharper.kickstart.location.VehicleLocationViewModel
import com.franklinharper.kickstart.location.VehicleLocations
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject

class MapFragment : Fragment() {

  @Inject
  lateinit var model: VehicleLocationViewModel

  @Inject
  lateinit var applicationContext: App

  private lateinit var map: GoogleMap
  private lateinit var binding: MapFragmentBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Only inflate the layout
    binding = DataBindingUtil.inflate(
      inflater,
      R.layout.map_fragment,
      container,
      false
    )
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    // Logic that operates on the View goes here
    App.appComponent.inject(this)
    setupMapView(savedInstanceState)
  }

  private fun setupMapView(savedInstanceState: Bundle?) {
    with(binding.mapView) {
      onCreate(savedInstanceState)
      getMapAsync {
        map = it
        observeModel()
        with(map) {
          uiSettings.isZoomControlsEnabled = true
          val losAngeles = LatLng(34.0522, -118.2437)
          val cameraUpdate = CameraUpdateFactory.newLatLngZoom(losAngeles, 10.0f)
          moveCamera(cameraUpdate)
        }
      }
    }
  }

  private fun observeModel() {
    model.vehicleLocationsLiveData.observe(viewLifecycleOwner) { vehicleLocations ->
      when (vehicleLocations) {
        null -> showError()
        else -> showVehicles(vehicleLocations)
      }
    }
  }

  private fun showVehicles(vehicleLocations: VehicleLocations) {
    map.clear()
    vehicleLocations.vehicles.forEach { vehicle ->
      val latLng = LatLng(vehicle.latitude, vehicle.longitude)
      val routeData = routeMap[vehicle.routeId]!!
      val icon = bitmapDescriptorFromVector(
        R.drawable.ic_train,
        ContextCompat.getColor(applicationContext, routeData.color)
      )
      val markerOptions = MarkerOptions()
        .position(latLng)
        .title("${routeData.displayName} train ${vehicle.vehicleId} ")
        .icon(icon)
      map.addMarker(markerOptions)
    }
  }

  private fun bitmapDescriptorFromVector(
    @DrawableRes vectorResId: Int,
    tint: Int
  ): BitmapDescriptor? {
    return ContextCompat.getDrawable(applicationContext, vectorResId)?.run {
      setTint(tint)
      setBounds(0, 0, intrinsicWidth, intrinsicHeight)
      val bitmap = Bitmap.createBitmap(
        intrinsicWidth,
        intrinsicHeight,
        Bitmap.Config.ARGB_8888
      )
      draw(Canvas(bitmap))
      BitmapDescriptorFactory.fromBitmap(bitmap)
    }
  }

  private fun showError() {
    Toast.makeText(
      applicationContext,
      R.string.error, Toast.LENGTH_SHORT
    ).show()
  }

  override fun onStart() {
    super.onStart()
    binding.mapView.onStart()
  }

  override fun onResume() {
    super.onResume()
    binding.mapView.onResume()
  }

  override fun onPause() {
    super.onPause()
    binding.mapView.onPause()
  }

  override fun onStop() {
    super.onStop()
    binding.mapView.onStop()
  }

  override fun onDestroy() {
    super.onDestroy()
    binding.mapView.onDestroy()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    binding.mapView.onSaveInstanceState(outState)
  }

  override fun onLowMemory() {
    super.onLowMemory()
    binding.mapView.onLowMemory()
  }

  private data class RouteData(val displayName: String, @ColorRes val color: Int)

  companion object {
    private val routeMap = mapOf(
      "801" to RouteData("Blue Line", R.color.light_blue),
      "802" to RouteData("Red Line", android.R.color.holo_red_dark),
      "803" to RouteData("Green Line", android.R.color.holo_green_dark),
      "804" to RouteData("Gold Line", R.color.gold),
      "805" to RouteData("Purple Line", R.color.purple),
      "806" to RouteData("Expo Line", android.R.color.holo_orange_dark)
    )
  }
}