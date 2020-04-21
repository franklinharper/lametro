package com.franklinharper.kickstart.dependencyinjection

import com.franklinharper.kickstart.location.map.MapFragment
import com.franklinharper.kickstart.location.list.ListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
  fun inject(c: ListFragment)
  fun inject(c: MapFragment)
}
