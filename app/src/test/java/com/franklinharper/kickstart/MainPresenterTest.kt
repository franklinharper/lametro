package com.franklinharper.kickstart

import android.content.Intent
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import org.junit.Rule
import org.junit.Test


class MainPresenterTest {
    @get:Rule
    val rule = RxSchedulerTestRule()

    val ui = mockk<MainUi>(relaxed = true)
    val location = mockk<Location>()
    val lat = 123.123
    val lon = -118.79
    val intent = mockk<Intent>()
    val api = mockk<LaMetroApi>(relaxed = true)
    val permissionManager = mockk<PermissionManager>(relaxed = true)
    val fusedLocationProviderClient = mockk<FusedLocationProviderClient>()
//    val price = Price(Pharmacy("CVS"), "5.59", "Coupon")
//    val prices = listOf(price)
    // sut is the System Under Test
    val sut = MainPresenterImpl(ui, api, permissionManager, fusedLocationProviderClient)

    init {
//        every { api.getVehicles(null, null) } returns Single.just(Vehicle(prices))
        every { permissionManager.checkStatus(Permission.ACCESS_COARSE_LOCATION) } returns PermissionStatus.GRANTED
        every { location.latitude } returns lat
        every { location.longitude } returns lon
    }

    @Test
    fun `when app launches search results are displayed`() {

        // Arrange

        // Act
        sut.onCreate(intent)

        // Assert
        verify {
//            ui.showResults(listOf(RecyclerViewItem.SearchResult(price)))
        }

    }

    @Test
    fun `when app launches the search is performed without a location`() {

        // Arrange

        // Act
        sut.onCreate(intent)

        // Assert
        verify {
//            api.getVehicles(null, null)
        }

    }

    @Test
    fun `search is performed with the zipcode when set by the user`() {

        // Arrange

        // Act
        sut.onCreate(intent)
        sut.searchByZipcode("90064")

        // Assert
        verifySequence {
            // verifies that these exact calls happened in the specified sequence
//            api.getVehicles(null, null)
//            api.getVehicles( null, "90064")
        }

    }

    @Test
    fun `when zipcode is set, and afterwards removed, the last search is performed without the zipcode`() {

        // Arrange

        // Act
        sut.onCreate(intent)
        sut.searchByZipcode("90064")
        sut.locationOptionClick(LocationOption.REMOVE_LOCATION)

        // Assert
        verifySequence {
            // verifies that these exact calls happened in the specified sequence
//            api.getVehicles(null, null)
//            api.getVehicles( null, "90064")
//            api.getVehicles( null, null)
        }

    }

    @Test
    fun `when location is set, and afterwards removed, the last search is performed without the location`() {

        // Arrange

        // Act
        sut.onCreate(intent)
        sut.searchByLocation(location)
        sut.locationOptionClick(LocationOption.REMOVE_LOCATION)

        // Assert
        verifySequence {
            // verifies that these exact calls happened in the specified sequence
//            api.getVehicles(null, null)
//            api.getVehicles( "$lat,$lon", null)
//            api.getVehicles( null, null)
        }

    }

    @Test
    fun `when zipcode is set, and afterwards replaced by location, then the last search is performed with the location`() {

        // Arrange

        // Act
        sut.onCreate(intent)
        sut.searchByZipcode("90064")
        sut.searchByLocation(location)

        // Assert
        verifySequence {
            // verifies that these exact calls happened in the specified sequence
//            api.getVehicles(null, null)
//            api.getVehicles( null, "90064")
//            api.getVehicles( "$lat,$lon", null)
        }

    }
}
