package com.franklinharper.kickstart

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

  @Provides
  @Singleton
  fun provideVehicleLocationViewModel(laMetroApi: LaMetroApi): VehicleLocationViewModel {
    return VehicleLocationViewModelImpl(laMetroApi)
  }

  @Provides
  @Singleton
  fun provideLaMetroApi(): LaMetroApi {
    val loggingInterceptor = HttpLoggingInterceptor().also {
      it.level = HttpLoggingInterceptor.Level.BODY
    }
    val clientBuilder = OkHttpClient.Builder().also {
      it.addInterceptor(loggingInterceptor)
    }
    val retrofit= Retrofit.Builder()
      .baseUrl("https://api.metro.net/")
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .client(clientBuilder.build())
      .addConverterFactory(GsonConverterFactory.create())
      .build()
    return retrofit.create(LaMetroApi::class.java)
  }

}
