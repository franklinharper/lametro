package com.franklinharper.kickstart

interface PermissionListener {

  fun onGranted(permission: Permission)
  fun onDenied(permission: Permission)

}
