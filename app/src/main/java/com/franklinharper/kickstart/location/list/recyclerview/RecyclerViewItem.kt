package com.franklinharper.kickstart.location.list.recyclerview

import com.franklinharper.kickstart.location.Vehicle
import com.franklinharper.kickstart.location.list.recyclerview.ViewType.*
import java.time.LocalDateTime

enum class ViewType {
  UPDATE_TIME,
  VEHICLE,
  ERROR
}

sealed class RecyclerViewItem(val type: ViewType) {
  data class UpdateTimeItem(val dateTime: LocalDateTime) : RecyclerViewItem(UPDATE_TIME)
  data class VehicleItem(val vehicle: Vehicle) : RecyclerViewItem(VEHICLE)
  data class ErrorItem(val errorMessage: String) : RecyclerViewItem(ERROR)
}

