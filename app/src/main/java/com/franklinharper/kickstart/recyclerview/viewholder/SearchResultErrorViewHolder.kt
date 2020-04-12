package com.franklinharper.kickstart.recyclerview.viewholder

import android.view.ViewGroup
import android.widget.TextView
import com.franklinharper.kickstart.R
import com.franklinharper.kickstart.recyclerview.RecyclerViewItem
import com.franklinharper.kickstart.recyclerview.adapter.SearchAdapter
import kotlinx.android.synthetic.main.item_search_error.view.*

class SearchResultErrorViewHolder(
  parent: ViewGroup,
  adapter: SearchAdapter
) : BaseSearchResultViewHolder(R.layout.item_search_error, parent, adapter) {

  private val message: TextView = itemView.searchErrorMessage

  init {
    itemView.retryButton.setOnClickListener { adapter.onRetryClick() }
  }

  override fun bind(position: Int) {
    val item = adapter.getItem(position) as RecyclerViewItem.Error
    message.text = item.errorMessage
  }
}