package com.franklinharper.kickstart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.franklinharper.kickstart.recyclerview.RecyclerViewItem
import com.nytimes.android.external.store3.base.impl.Store
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface VehicleLocationViewModel {
  val searchResults: LiveData<List<RecyclerViewItem>>
  val loading: LiveData<Boolean>
  fun startLocationQuery()
}

class VehicleLocationViewModelImpl @Inject constructor(
  private val vehicleLocationStore: Store<Vehicles, String>,
  private val localDb: LocalDb
) : VehicleLocationViewModel, ViewModel() {

  override val searchResults = MutableLiveData<List<RecyclerViewItem>>()
  override val loading = MutableLiveData<Boolean>()

  private val compositeDisposable = CompositeDisposable()

  override fun startLocationQuery() {
    searchResults.value = emptyList()
    loading.value = true
    compositeDisposable += Observable.interval(0, 10, TimeUnit.SECONDS)
      .flatMapSingle {
        val utc = OffsetDateTime.now(ZoneOffset.UTC);
        val key = DateTimeFormatter.ISO_DATE_TIME.format(utc)
        vehicleLocationStore.get(key)
      }
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        { vehicles ->
          saveToLocalDb(vehicles)
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

  private fun saveToLocalDb(vehicles: Vehicles) {
    val timeStamp = Timestamp.from(ZonedDateTime.now())
    with(localDb) {
      transaction {
        vehicles.vehicles.forEach { vehicle ->
          with(vehicle) {
            localDb.queries.insert(
             vehicleId,
              routeId,
              runId,
              latitude,
              longitude,
              heading,
              secondsSinceReport,
              predictable,
              timeStamp
            )
          }
        }
      }
    }
  }

}