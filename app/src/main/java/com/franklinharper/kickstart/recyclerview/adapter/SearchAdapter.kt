package com.franklinharper.kickstart.recyclerview.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.franklinharper.kickstart.recyclerview.RecyclerViewItem
import com.franklinharper.kickstart.recyclerview.ViewType
import com.franklinharper.kickstart.recyclerview.viewholder.BaseSearchResultViewHolder
import com.franklinharper.kickstart.recyclerview.viewholder.VehicleViewHolder
import com.franklinharper.kickstart.recyclerview.viewholder.SearchResultErrorViewHolder


class SearchAdapter(
    val context: Context
) : RecyclerView.Adapter<BaseSearchResultViewHolder>() {

    private val items = mutableListOf<RecyclerViewItem>()
    private lateinit var retryClickListener:() -> Unit

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = items[position].type.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : BaseSearchResultViewHolder {
        val type: ViewType = ViewType.values()[viewType]
        return when (type) {
            ViewType.ERROR -> SearchResultErrorViewHolder(parent, this)
            ViewType.VEHICLE -> VehicleViewHolder(parent, this)
        }
    }

    override fun onBindViewHolder(holder: BaseSearchResultViewHolder, position: Int) {
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
