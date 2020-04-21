package com.franklinharper.kickstart.location.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.franklinharper.kickstart.App
import com.franklinharper.kickstart.R
import com.franklinharper.kickstart.location.VehicleLocationViewModel
import com.franklinharper.kickstart.location.VehicleLocations
import com.franklinharper.kickstart.databinding.ListFragmentBinding
import com.franklinharper.kickstart.location.list.recyclerview.DividerItemDecoration
import com.franklinharper.kickstart.location.list.recyclerview.RecyclerViewItem
import com.franklinharper.kickstart.location.list.recyclerview.adapter.Adapter
import java.time.LocalDateTime
import javax.inject.Inject

class ListFragment : Fragment() {

  @Inject
  lateinit var model: VehicleLocationViewModel

  private lateinit var binding: ListFragmentBinding
  private val searchAdapter: Adapter by lazy { Adapter((requireActivity().baseContext)) }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Only inflate the layout
    binding = DataBindingUtil.inflate(
      inflater,
      R.layout.list_fragment,
      container,
      false
    )
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    // Logic that operates on the View goes here
    App.appComponent.inject(this)
    configureRecyclerView()
    observeUi()
    observeModel()
    model.startVehicleLocationUpdates()
  }

  private fun observeUi() {
    searchAdapter.setRetryClickLister {
      model.startVehicleLocationUpdates()
    }
  }

  private fun observeModel() {
    model.vehicleLocationsLiveData.observe(viewLifecycleOwner, Observer { vehicleLocations ->
      when(vehicleLocations) {
        null -> showError()
        else -> showVehicles(vehicleLocations)
      }
    })
    model.loading.observe(viewLifecycleOwner, Observer { loading ->
      binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
    })
  }

  private fun showVehicles(vehicles: VehicleLocations) {
    val newItems = mutableListOf<RecyclerViewItem>(
      RecyclerViewItem.UpdateTimeItem(LocalDateTime.now())
    )
    vehicles.vehicles.forEach { vehicle ->
      newItems.add(RecyclerViewItem.VehicleItem(vehicle))
    }
    searchAdapter.setItems(newItems)
  }

  private fun showError() {
    searchAdapter.setItems(listOf(
        RecyclerViewItem.ErrorItem("Vehicle locations unavailable")
      ))
  }

  private fun configureRecyclerView() {
    binding.searchResultsList.layoutManager = LinearLayoutManager(
      activity,
      RecyclerView.VERTICAL,
      false
    )
    binding.searchResultsList.addItemDecoration(
      DividerItemDecoration(
        ContextCompat.getDrawable(
          requireActivity().baseContext,
          R.drawable.divider
        )
      )
    )
    binding.searchResultsList.adapter = searchAdapter
  }

}