package com.franklinharper.kickstart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.franklinharper.kickstart.databinding.FragmentListBinding
import com.franklinharper.kickstart.recyclerview.DividerItemDecoration
import com.franklinharper.kickstart.recyclerview.adapter.Adapter

class ListFragment : Fragment() {

  private lateinit var binding: FragmentListBinding
  private val searchAdapter: Adapter by lazy { Adapter((requireActivity().baseContext)) }
  private val model: VehiculeLocationViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
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
    model.searchResults.observe(viewLifecycleOwner, Observer { newItems ->
      searchAdapter.setItems(newItems)
    })
    model.loading.observe(viewLifecycleOwner, Observer { loading ->
      binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
    })
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