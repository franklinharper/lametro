package com.franklinharper.kickstart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.franklinharper.kickstart.recyclerview.RecyclerViewItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface VehicleLocationViewModel  {
  val searchResults: LiveData<List<RecyclerViewItem>>
  val loading: LiveData<Boolean>
  fun startLocationQuery()
}

class VehicleLocationViewModelImpl @Inject constructor(
  private val laMetroApi: LaMetroApi
) : VehicleLocationViewModel, ViewModel() {

  override val searchResults = MutableLiveData<List<RecyclerViewItem>>()
  override val loading = MutableLiveData<Boolean>()

  private val compositeDisposable = CompositeDisposable()

  override fun startLocationQuery() {
    searchResults.value = emptyList()
    loading.value = true
    compositeDisposable += Observable.interval(0, 10, TimeUnit.SECONDS)
      .flatMap {
        laMetroApi.getVehicles("lametro-rail")
      }
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        { vehicles ->
          loading.value = false
          val newItems = mutableListOf<RecyclerViewItem>(
            RecyclerViewItem.UpdateTimeItem(LocalDateTime.now())
          )
          vehicles.vehicles.forEach { vehicle ->
            newItems.add(RecyclerViewItem.VehicleItem(vehicle))
          }
          searchResults.value = newItems
        }, { throwable ->
          loading.value = false
          when (throwable) {
            is UnknownHostException -> {
              searchResults.value = listOf(
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