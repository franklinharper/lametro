package com.franklinharper.kickstart.dependencyinjection

import com.franklinharper.kickstart.*
import com.franklinharper.kickstart.location.*
import com.nytimes.android.external.store3.base.Fetcher
import com.nytimes.android.external.store3.base.impl.FluentStoreBuilder
import com.nytimes.android.external.store3.base.impl.Store
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(
  private val app: App
) {

  @Provides
  @Singleton
  fun provideApp() = app

  @Provides
  @Singleton
  fun provideVehicleLocationViewModel(
    store: Store<VehicleLocations, String>,
    localDb: LocalDb
  ): VehicleLocationViewModel {
    return VehicleLocationViewModelImpl(
      store,
      localDb
    )
  }

  @Provides
  @Singleton
  fun provideVehicleLocationStore(laMetroApi: LaMetroApi): Store<VehicleLocations, String> {
    return FluentStoreBuilder.key(
      fetcher = Fetcher<VehicleLocations, String> { key ->
        // Ignore the key because we always want the latest data
        laMetroApi.getVehicleLocations("lametro-rail")
      }
    )
  }

  @Provides
  @Singleton
  fun provideLocalDb(app: App) =
    LocalDb(app)

  @Provides
  @Singleton
  fun provideLaMetroApi(): LaMetroApi {
    val loggingInterceptor = HttpLoggingInterceptor().also {
      it.level = HttpLoggingInterceptor.Level.BODY
    }
    val clientBuilder = OkHttpClient.Builder().also {
      it.addInterceptor(loggingInterceptor)
    }
    val retrofit = Retrofit.Builder()
      .baseUrl("https://api.metro.net/")
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .client(clientBuilder.build())
      .addConverterFactory(GsonConverterFactory.create())
      .build()
    return retrofit.create(LaMetroApi::class.java)
  }

}
