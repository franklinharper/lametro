package com.franklinharper.kickstart.recyclerview.viewholder

import android.view.ViewGroup
import android.widget.TextView
import com.franklinharper.kickstart.R
import com.franklinharper.kickstart.recyclerview.RecyclerViewItem
import com.franklinharper.kickstart.recyclerview.adapter.Adapter
import kotlinx.android.synthetic.main.item_vehicle.view.*

class VehicleViewHolder(
    parent: ViewGroup,
    adapter: Adapter
) : BaseViewHolder(R.layout.item_vehicle, parent, adapter) {

    private val idView: TextView = itemView.vehicleId
    private val locationView: TextView = itemView.location
    private val headingView: TextView = itemView.heading

    init {
//    itemView.onClick {
//      if (adapterPosition != RecyclerView.NO_POSITION) {
//        val item = adapter.getItem(adapterPosition) as SearchResultItem
//      }
//    }
    }

    override fun bind(position: Int) {
        val item = adapter.getItem(position) as RecyclerViewItem.VehicleItem
        with(item.vehicle) {
            idView.text = vehicleId
            locationView.text = "$latitude, $longitude"
            headingView.text = heading.toString()
        }
    }

}