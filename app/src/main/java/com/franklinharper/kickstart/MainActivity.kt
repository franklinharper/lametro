package com.franklinharper.kickstart

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.franklinharper.kickstart.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

  private lateinit var appBarConfiguration: AppBarConfiguration

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
      this,
      R.layout.activity_main
    )
    val drawerLayout = binding.drawerLayout
    val navController = findNavController(R.id.mainNavHostFragment)
    NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
    appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
    NavigationUI.setupWithNavController(binding.navView, navController)
//    binding.bottomNav.setupWithNavController(navController)
  }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        permissionManager.onRequestPermissionsResult(requestCode, grantResults)
//    }

  override fun onSupportNavigateUp(): Boolean {
    val navController = this.findNavController(R.id.mainNavHostFragment)
    return NavigationUI.navigateUp(navController, appBarConfiguration)
  }
}
