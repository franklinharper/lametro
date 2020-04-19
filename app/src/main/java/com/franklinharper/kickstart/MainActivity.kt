package com.franklinharper.kickstart

import android.os.Bundle
import android.view.MenuInflater
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.franklinharper.kickstart.databinding.MainActivityBinding

class MainActivity : BaseActivity() {

  private lateinit var binding: MainActivityBinding
  private lateinit var navController: NavController
  private lateinit var appBarConfiguration: AppBarConfiguration

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
    val navHostFragment = supportFragmentManager.findFragmentById(
      R.id.mainNavHostFragment
    ) as NavHostFragment
    navController = navHostFragment.navController
    appBarConfiguration = AppBarConfiguration(
      topLevelDestinationIds = setOf(
        R.id.listFragment,
        R.id.mapFragment,
        R.id.aboutFragment
      )
    )
    NavigationUI.setupActionBarWithNavController(
      this,
      navController,
      appBarConfiguration
    )
    NavigationUI.setupWithNavController(binding.bottomNav, navController)
  }

  override fun onSupportNavigateUp(): Boolean {
    return NavigationUI.navigateUp(navController, appBarConfiguration)
  }
}
