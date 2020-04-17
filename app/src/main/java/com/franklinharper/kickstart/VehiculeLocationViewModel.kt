package com.franklinharper.kickstart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.franklinharper.kickstart.recyclerview.RecyclerViewItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.UnknownHostException
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

class VehiculeLocationViewModel: ViewModel() {

  private val loggingInterceptor = HttpLoggingInterceptor().also {
    it.level = HttpLoggingInterceptor.Level.BODY
  }
  private val clientBuilder = OkHttpClient.Builder().also {
    it.addInterceptor(loggingInterceptor)
  }

  // Retrofit.build() returns a Platform Type (Java code).
  // Declaring the explicit type lets Kotlin know that the `retrotfit` property is not nullable.
  private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://api.metro.net/")
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .client(clientBuilder.build())
    .addConverterFactory(GsonConverterFactory.create())
    .build()

  private val laMetroApi: LaMetroApi = retrofit.create(LaMetroApi::class.java)
  private val compositeDisposable = CompositeDisposable()

  private val _searchResults = MutableLiveData<List<RecyclerViewItem>>()
  // Hide the MutableLiveData, to prevent exposing mutatability to outside classes
  val searchResults : LiveData<List<RecyclerViewItem>> = _searchResults

  private val _loading = MutableLiveData(false)
  val loading : LiveData<Boolean> = _loading

  fun startLocationQuery() {
    _searchResults.value = emptyList()
    _loading.value = true
    compositeDisposable += Observable.interval(0, 10, TimeUnit.SECONDS)
      .flatMap {
        laMetroApi.getVehicles("lametro-rail")
      }
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        { vehicles ->
          _loading.value = false
          val newItems = mutableListOf<RecyclerViewItem>(
            RecyclerViewItem.UpdateTimeItem(LocalDateTime.now())
          )
          vehicles.vehicles.forEach { vehicle ->
            newItems.add(RecyclerViewItem.VehicleItem(vehicle))
          }
          _searchResults.value = newItems
        }, { throwable ->
          _loading.value = false
          when (throwable) {
            is UnknownHostException -> {
              _searchResults.value = listOf(
                RecyclerViewItem.ErrorItem("Unable to connect to server")
              )
            }
            // Crash to know about bugs ASAP
            // TODO productionize crash handling (e.g. use Crashlytics)
            else -> throw throwable
          }
        }
      )
  }
}