package com.franklinharper.kickstart

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.franklinharper.kickstart.databinding.MapFragmentBinding
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

  private lateinit var map: GoogleMap
  private lateinit var binding: MapFragmentBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    App.appComponent.inject(this)
    binding = DataBindingUtil.inflate(
      inflater,
      R.layout.map_fragment,
      container,
      false
    )
    configureMapView(savedInstanceState)
    return binding.root
  }

  private fun configureMapView(savedInstanceState: Bundle?) {
    val mv = binding.mapView
    with(mv) {
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
    model.vehicleLocations.observe(viewLifecycleOwner, Observer { vehicleLocations ->
      when (vehicleLocations) {
        null -> showError()
        else -> showVehicles(vehicleLocations)
      }
    })
//    model.loading.observe(viewLifecycleOwner, Observer { loading ->
//    })
  }

  private fun showVehicles(vehicleLocations: VehicleLocations) {
    map.clear()
    vehicleLocations.vehicles.forEach { vehicle ->
      val latLng = LatLng(vehicle.latitude, vehicle.longitude)
      val routeData = routeMap[vehicle.routeId]!!
      val icon = bitmapDescriptorFromVector(
        requireContext(),
        R.drawable.ic_train,
        ContextCompat.getColor(requireContext(), routeData.color)
      )
      val markerOptions = MarkerOptions()
        .position(latLng)
        .title("${routeData.displayName} train ${vehicle.vehicleId} ")
        .icon(icon)
      map.addMarker(markerOptions)
    }
  }

  private fun showError() {
    TODO("Not yet implemented")
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

  private fun bitmapDescriptorFromVector(
    context: Context,
    @DrawableRes vectorResId: Int,
    tint: Int
  ): BitmapDescriptor? {
    return ContextCompat.getDrawable(context, vectorResId)?.run {
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

  private data class routeData(val displayName: String, @ColorRes val color: Int)

  companion object {
    private val routeMap = mapOf(
      "801" to routeData("Blue Line", R.color.light_blue),
      "802" to routeData("Red Line", android.R.color.holo_red_dark),
      "803" to routeData("Green Line", android.R.color.holo_green_dark),
      "804" to routeData("Gold Line", R.color.gold),
      "805" to routeData("Purple Line", R.color.purple),
      "806" to routeData("Expo Line", android.R.color.holo_orange_dark)
    )
  }
}