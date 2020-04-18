package com.franklinharper.kickstart

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
import com.franklinharper.kickstart.databinding.FragmentListBinding
import com.franklinharper.kickstart.recyclerview.DividerItemDecoration
import com.franklinharper.kickstart.recyclerview.RecyclerViewItem
import com.franklinharper.kickstart.recyclerview.adapter.Adapter
import java.time.LocalDateTime
import javax.inject.Inject

class ListFragment : Fragment() {

  @Inject
  lateinit var model: VehicleLocationViewModel

  private lateinit var binding: FragmentListBinding
  private val searchAdapter: Adapter by lazy { Adapter((requireActivity().baseContext)) }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    App.appComponent.inject(this)
    binding = DataBindingUtil.inflate(
      inflater,
      R.layout.fragment_list,
      container,
      false
    )
    configureRecyclerView()
    observeUi()
    observeModel()
    model.startLocationQuery()
    return binding.root
  }

  private fun observeUi() {
    searchAdapter.setRetryClickLister {
      model.startLocationQuery()
    }
  }

  private fun observeModel() {
    model.vehicleLocations.observe(viewLifecycleOwner, Observer { vehicleLocations ->
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