package com.franklinharper.kickstart.location.map

import com.franklinharper.kickstart.location.map.Permission

interface PermissionListener {

  fun onGranted(permission: Permission)
  fun onDenied(permission: Permission)

}
