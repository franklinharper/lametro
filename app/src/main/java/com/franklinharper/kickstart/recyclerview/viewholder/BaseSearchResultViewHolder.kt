package com.franklinharper.kickstart.recyclerview.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.franklinharper.kickstart.recyclerview.adapter.Adapter

abstract class BaseSearchResultViewHolder(
  @LayoutRes layout: Int,
  parent: ViewGroup,
  val adapter: Adapter
) : RecyclerView.ViewHolder(LayoutInflater.from(adapter.context).inflate(layout, parent, false)) {

  /**
   * Base abstract bind for all view models
   *
   * @param position Index of items
   */
  abstract fun bind(position: Int)
}