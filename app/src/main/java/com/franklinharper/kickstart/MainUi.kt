package com.franklinharper.kickstart

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.franklinharper.kickstart.recyclerview.DividerItemDecoration
import com.franklinharper.kickstart.recyclerview.RecyclerViewItem
import com.franklinharper.kickstart.recyclerview.adapter.Adapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder

interface MainUi {
//    fun onCreate(mainPresenter: MainPresenter)
//    fun showResults(items: List<RecyclerViewItem>)
//    fun showLoading(visible: Boolean)
//    fun showError(searchResultItem: RecyclerViewItem)
//    fun showLocationOptionsDialog(options: Array<String>)
//    fun showLocationPermissionDenied()
//    fun showEnterLocationDialog()
}

class MainUiImpl(
    private val activity: MainActivity
) : MainUi {

//    private lateinit var presenter: MainPresenter
//    private val searchAdapter = Adapter(activity)
//
//    // TODO remove calls to findViewById, and replace with kotlinx.android.synthetic.
//    private val searchResultsList = activity.findViewById<RecyclerView>(R.id.searchResultsList)
//    private val progressBar = activity.findViewById<ProgressBar>(R.id.progressBar)
//    private val setLocationButton = activity.findViewById<Button>(R.id.setLocationButton)
//
//    override fun onCreate(mainPresenter: MainPresenter) {
//        presenter = mainPresenter
//        setLocationButton.setOnClickListener { mainPresenter.onSetLocationClick() }
//    }
//
//    override fun showError(searchResultItem: RecyclerViewItem) {
//        searchAdapter.setItems(listOf(searchResultItem))
//    }
//
//    override fun showLocationOptionsDialog(options: Array<String>) {
//        // TODO use BottomSheetDialog to provide better UX
////        val dialog = BottomSheetDialog(activity)
////        val bottomSheet = activity.layoutInflater.inflate(R.layout.bottom_sheet, null)
////        bottomSheet.buttonSubmit.setOnClickListener { dialog.dismiss() }
////        dialog.setContentView(bottomSheet)
////        dialog.show()
//        MaterialAlertDialogBuilder(activity)
//            .setTitle("Set location")
//            .setItems(options) { _, which ->
//                val option = when(which) {
//                    0 -> LocationOption.CURRENT_LOCATION
//                    1 -> LocationOption.ENTER_LOCATION
//                    2 -> LocationOption.REMOVE_LOCATION
//                    else -> throw IllegalStateException()
//                }
//                presenter.locationOptionClick(option)
//            }
//            .setNegativeButton(android.R.string.cancel, null)
//            .show()
//    }
//
//    override fun showLocationPermissionDenied() {
//        Toast.makeText(activity, "Location permission denied", Toast.LENGTH_SHORT).show()
//    }
//
//    override fun showEnterLocationDialog() {
//        val view = activity.layoutInflater.inflate(R.layout.dialog_zip_code, null)
//
//        val categoryEditText = view.findViewById(R.id.categoryEditText) as EditText
//
//        MaterialAlertDialogBuilder(activity)
//            .setTitle("Enter valid US zip code")
//            .setView(view)
//            .setPositiveButton(android.R.string.ok) { _, _ ->
//                presenter.searchByZipcode(categoryEditText.text.toString())
//            }
//            .setNegativeButton(android.R.string.cancel, null)
//            .show()
//    }
//
//    override fun showResults(items: List<RecyclerViewItem>) {
//        searchAdapter.setItems(items)
//    }
//
//    override fun showLoading(visible: Boolean) {
//        progressBar.visibility = if (visible) View.VISIBLE else View.GONE
//    }

}