package com.franklinharper.kickstart.location.list.recyclerview.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.franklinharper.kickstart.location.list.recyclerview.RecyclerViewItem
import com.franklinharper.kickstart.location.list.recyclerview.ViewType
import com.franklinharper.kickstart.location.list.recyclerview.viewholder.BaseViewHolder
import com.franklinharper.kickstart.location.list.recyclerview.viewholder.SearchResultErrorViewHolder
import com.franklinharper.kickstart.location.list.recyclerview.viewholder.UpdateTimeViewHolder
import com.franklinharper.kickstart.location.list.recyclerview.viewholder.VehicleViewHolder


class Adapter(val context: Context) : RecyclerView.Adapter<BaseViewHolder>() {

    private val items = mutableListOf<RecyclerViewItem>()
    private lateinit var retryClickListener:() -> Unit

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = items[position].type.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : BaseViewHolder {
        val type: ViewType = ViewType.values()[viewType]
        return when (type) {
            ViewType.UPDATE_TIME -> UpdateTimeViewHolder(parent, this)
            ViewType.VEHICLE -> VehicleViewHolder(parent, this)
            ViewType.ERROR -> SearchResultErrorViewHolder(parent, this)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(position)
    }

    fun setRetryClickLister(listener: () -> Unit) {
        this.retryClickListener = listener
    }

    fun onRetryClick() {
        retryClickListener()
    }

    fun setItems(newItems: List<RecyclerViewItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun getItem(position: Int) = items[position]
}
