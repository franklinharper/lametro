package com.franklinharper.kickstart.recyclerview.viewholder

import android.view.ViewGroup
import android.widget.TextView
import com.franklinharper.kickstart.R
import com.franklinharper.kickstart.recyclerview.RecyclerViewItem
import com.franklinharper.kickstart.recyclerview.adapter.Adapter
import kotlinx.android.synthetic.main.item_update_time.view.*
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class UpdateTimeViewHolder(
    parent: ViewGroup,
    adapter: Adapter
) : BaseSearchResultViewHolder(R.layout.item_update_time, parent, adapter) {

    private val updateTime: TextView = itemView.updateTime
    private val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)

    init {
//    itemView.onClick {
//      if (adapterPosition != RecyclerView.NO_POSITION) {
//        val item = adapter.getItem(adapterPosition) as SearchResultItem
//      }
//    }
    }

    override fun bind(position: Int) {
        val item = adapter.getItem(position) as RecyclerViewItem.UpdateTimeItem
        updateTime.text = item.dateTime.format(formatter)
    }

}