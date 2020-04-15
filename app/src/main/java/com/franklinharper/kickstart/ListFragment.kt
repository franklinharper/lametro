package com.franklinharper.kickstart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.franklinharper.kickstart.databinding.FragmentListBinding
import com.franklinharper.kickstart.recyclerview.DividerItemDecoration
import com.franklinharper.kickstart.recyclerview.RecyclerViewItem
import com.franklinharper.kickstart.recyclerview.adapter.Adapter
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

class ListFragment : Fragment() {

  private lateinit var binding: FragmentListBinding
  private lateinit var searchAdapter: Adapter

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

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(
      inflater,
      R.layout.fragment_list,
      container,
      false
    )
    configureRecyclerView()
    search()
    return binding.root
  }

  private fun configureRecyclerView() {
    binding.searchResultsList.layoutManager = LinearLayoutManager(
      activity,
      RecyclerView.VERTICAL,
      false
    )
    binding.searchResultsList.addItemDecoration(
      DividerItemDecoration(
        ContextCompat.getDrawable(
          requireActivity().baseContext,
          R.drawable.divider
        )
      )
    )
    searchAdapter = Adapter((requireActivity().baseContext))
    searchAdapter.setRetryClickLister {
      search()
    }
    binding.searchResultsList.adapter = searchAdapter
  }

  private fun showResults(items: List<RecyclerViewItem>) {
    searchAdapter.setItems(items)
  }

  private fun showLoading(visible: Boolean) {
    binding.progressBar.visibility = if (visible) View.VISIBLE else View.GONE
  }

  fun showError(searchResultItem: RecyclerViewItem) {
    searchAdapter.setItems(listOf(searchResultItem))
  }

  private fun search() {
    showResults(emptyList())
    showLoading(true)
    compositeDisposable += Observable.interval(0, 10, TimeUnit.SECONDS)
      .flatMap {
        laMetroApi.getVehicles("lametro-rail")
      }
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        { vehicles ->
          showLoading(false)
          val newItems = mutableListOf<RecyclerViewItem>(
            RecyclerViewItem.UpdateTimeItem(LocalDateTime.now())
          )
          vehicles.vehicles.forEach { vehicle ->
            newItems.add(RecyclerViewItem.VehicleItem(vehicle))
          }
          showResults(newItems)
        }, { throwable ->
          showLoading(false)
          when (throwable) {
            is UnknownHostException -> {
              showError(RecyclerViewItem.ErrorItem("Unable to connect to server"))
            }
            // Crash to know about bugs ASAP
            // TODO productionize crash handling (e.g. use Crashlytics)
            else -> throw throwable
          }
        }
      )
  }
}