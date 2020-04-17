package com.franklinharper.kickstart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.franklinharper.kickstart.databinding.FragmentListBinding
import com.franklinharper.kickstart.databinding.MapFragmentBinding

class MapFragment : Fragment() {
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val binding: MapFragmentBinding = DataBindingUtil.inflate(
      inflater,
      R.layout.map_fragment,
      container,
      false
    )
    binding.tv.text = "Hello Map!"
    return binding.root
  }
}