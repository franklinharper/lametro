package com.franklinharper.kickstart

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.franklinharper.kickstart.databinding.MainActivityBinding

class MainActivity : BaseActivity() {

  private lateinit var binding: MainActivityBinding
  private val navController: NavController by lazy {
    Navigation.findNavController(this, R.id.mainNavHostFragment)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
    configureNavigation()
  }

  private fun configureNavigation() {
    NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)
    NavigationUI.setupWithNavController(binding.bottomNav, navController)
  }

  override fun onSupportNavigateUp(): Boolean {
    val appBarConfiguration = AppBarConfiguration(
      navController.graph,
      binding.drawerLayout
    )
    return NavigationUI.navigateUp(navController, appBarConfiguration)
  }
}
