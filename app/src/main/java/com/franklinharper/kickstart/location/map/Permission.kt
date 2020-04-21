package com.franklinharper.kickstart.location.map

import android.Manifest
import androidx.annotation.StringRes
import com.franklinharper.kickstart.R


/**
 * All "dangerous" permissions that we need to ask user for when targeting SDK 23+.

 * @see [](https://docs.google.com/document/d/1wFRlCJ0cM7dy5VNXXEDke7KkAH0GQg15IaSijMAa9TA/edit?ts=57ed7f72.></a>
) */
enum class Permission constructor(
  /**
   * Request code permission, similar to activity request code, must be unique
   */
  val requestCode: Int,
  /**
   * The permission string from Android manifest. We can’t use `name` because it is an enum, and we
   * considered alternatives (e.g. `id`), but didn't find anything better, so we decided to stick
   * with permission. https://ziprecruiter.slack.com/archives/C950J84FQ/p1580766797125200
   */
  val permission: String,
  /**
   * The rationale message to explain why the app need this permission.
   */
  @param:StringRes val rationale: Int
) {
  ACCESS_COARSE_LOCATION(
    778,
    Manifest.permission.ACCESS_COARSE_LOCATION,
    R.string.permission_rationale_location
  ),

  READ_CONTACTS(
    780,
    Manifest.permission.READ_CONTACTS,
    R.string.permission_rationale_read_contacts
  )
}
