package com.franklinharper.kickstart

import android.app.Application
import com.franklinharper.kickstart.dependencyinjection.AppComponent
import com.franklinharper.kickstart.dependencyinjection.AppModule
import com.franklinharper.kickstart.dependencyinjection.DaggerAppComponent
import timber.log.Timber

class App : Application() {

  override fun onCreate() {
    super.onCreate()
    // Start out with simple Dependency Injection.
    // A more sophisticated approach will become necessary once tests are
    // added to the project.
    appComponent = DaggerAppComponent
      .builder()
      .appModule(AppModule(this))
      .build()
    if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
  }

  companion object {
    lateinit var appComponent: AppComponent
  }

}