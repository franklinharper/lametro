package com.franklinharper.kickstart.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.franklinharper.kickstart.*
import com.franklinharper.kickstart.extension.plusAssign
import com.nytimes.android.external.store3.base.impl.Store
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface VehicleLocationViewModel {
  val vehicleLocationsLiveData: LiveData<VehicleLocations>
  val loading: LiveData<Boolean>
  fun startVehicleLocationUpdates()
}

class VehicleLocationViewModelImpl @Inject constructor(
  private val vehicleLocationStore: Store<VehicleLocations, String>,
  private val localDb: LocalDb
) : VehicleLocationViewModel, ViewModel() {

  override val vehicleLocationsLiveData = MutableLiveData<VehicleLocations>()
  override val loading = MutableLiveData<Boolean>()
  private val compositeDisposable = CompositeDisposable()

  override fun onCleared() {
    super.onCleared()
    Timber.i("onCleared() called")
    compositeDisposable.clear()
  }

  override fun startVehicleLocationUpdates() {
    vehicleLocationsLiveData.value =
      VehicleLocations()
    loading.value = true
    compositeDisposable += Observable.interval(0, 10, TimeUnit.SECONDS)
      .flatMapSingle {
        val utc = OffsetDateTime.now(ZoneOffset.UTC);
        val key = DateTimeFormatter.ISO_DATE_TIME.format(utc)
        vehicleLocationStore.get(key)
      }
      .doOnNext { vehicleLocations ->
        saveToLocalDb(vehicleLocations)
      }
      .map { vehicleLocations ->
        // The API doesn't return the vehicles in a stable order, so we'll sort the list
        val sorted = vehicleLocations.vehicles.sortedBy { it.vehicleId }
        vehicleLocations.copy(vehicles = sorted)
      }
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        { vehicleLocations ->
          loading.value = false
          vehicleLocationsLiveData.value = vehicleLocations
        }, { throwable ->
          loading.value = false
          vehicleLocationsLiveData.value = null
          reportOrThrow(throwable)
        }
      )
  }

  private fun reportOrThrow(throwable: Throwable) {
    if (BuildConfig.DEBUG && throwable !is UnknownHostException && throwable !is SocketTimeoutException) {
      // In dev crash to know about bugs ASAP
      throw throwable
    }
    // TODO report crashes in production (e.g. use Crashlytics)
  }

  private fun saveToLocalDb(vehicles: VehicleLocations) {
    val timeStamp =
      Timestamp.from(ZonedDateTime.now())
    with(localDb) {
      transaction {
        vehicles.vehicles.forEach { vehicle ->
          with(vehicle) {
            localDb.queries.insert(
              vehicleId,
              routeId,
              runId,
              latitude.toString(),
              longitude.toString(),
              heading.toString(),
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