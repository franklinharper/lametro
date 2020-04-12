package com.franklinharper.kickstart.recyclerview

import com.franklinharper.kickstart.Vehicle
import com.franklinharper.kickstart.recyclerview.ViewType.VEHICLE
import com.franklinharper.kickstart.recyclerview.ViewType.ERROR

enum class ViewType {
  VEHICLE,
  ERROR
}

sealed class RecyclerViewItem(val type: ViewType) {
  data class SearchResult(val vehicle: Vehicle) : RecyclerViewItem(VEHICLE)
  data class Error(val errorMessage: String) : RecyclerViewItem(ERROR)
}

