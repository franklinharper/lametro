package com.franklinharper.kickstart.location.list.recyclerview.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.franklinharper.kickstart.location.list.recyclerview.adapter.Adapter

abstract class BaseViewHolder(
  @LayoutRes layout: Int,
  parent: ViewGroup,
  val adapter: Adapter
) : RecyclerView.ViewHolder(
  LayoutInflater.from(adapter.context).inflate(layout, parent, false)
) {

  abstract fun bind(position: Int)
}