package com.franklinharper.kickstart

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
  fun inject(c: ListFragment)
  fun inject(c: MapFragment)
}
