package com.franklinharper.kickstart

import android.app.Application
import timber.log.Timber

class App : Application() {

  override fun onCreate() {
    super.onCreate()
    appComponent = DaggerAppComponent
      .builder()
      .appModule(AppModule())
      .build()
    if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
  }

  companion object {
    lateinit var appComponent: AppComponent
  }

}