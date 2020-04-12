package com.franklinharper.kickstart.recyclerview.viewholder

import android.view.ViewGroup
import android.widget.TextView
import com.franklinharper.kickstart.R
import com.franklinharper.kickstart.recyclerview.RecyclerViewItem
import com.franklinharper.kickstart.recyclerview.adapter.SearchAdapter
import kotlinx.android.synthetic.main.item_vehicle.view.*

class VehicleViewHolder(
    parent: ViewGroup,
    adapter: SearchAdapter
) : BaseSearchResultViewHolder(R.layout.item_vehicle, parent, adapter) {

    private val vehicleId: TextView = itemView.vehicleId
    private val location: TextView = itemView.location

    init {
//    itemView.onClick {
//      if (adapterPosition != RecyclerView.NO_POSITION) {
//        val item = adapter.getItem(adapterPosition) as SearchResultItem
//      }
//    }
    }

    override fun bind(position: Int) {
        val item = adapter.getItem(position) as RecyclerViewItem.SearchResult
        with(item.vehicle) {
            vehicleId.text = id
            location.text = "$latitude, $longitude"
        }
    }

}