package com.example.jan_aushadhifinder

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity :
    FragmentActivity(),
    OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private lateinit var fusedLocationClient:
            FusedLocationProviderClient

    // STORE MODEL
    data class Store(
        val name: String,
        val location: LatLng,
        val isOpen: Boolean
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.map)

        fusedLocationClient =
            LocationServices
                .getFusedLocationProviderClient(this)

        val mapFragment =
            supportFragmentManager
                .findFragmentById(R.id.map)
                    as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(
        googleMap: GoogleMap
    ) {

        mMap = googleMap

        // CHECK LOCATION PERMISSION
        if (
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                100
            )

            return
        }

        // ENABLE LIVE LOCATION
        mMap.isMyLocationEnabled = true

        // LOCATION REQUEST
        val locationRequest =
            LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                2000
            ).build()

        // LIVE LOCATION UPDATES
        fusedLocationClient.requestLocationUpdates(

            locationRequest,

            object : LocationCallback() {

                override fun onLocationResult(
                    result: LocationResult
                ) {

                    val location =
                        result.lastLocation ?: return

                    // USER LOCATION
                    val userLocation =
                        LatLng(
                            location.latitude,
                            location.longitude
                        )

                    // CLEAR OLD MARKERS
                    mMap.clear()

                    // MOVE CAMERA
                    mMap.animateCamera(
                        CameraUpdateFactory
                            .newLatLngZoom(
                                userLocation,
                                14f
                            )
                    )

                    // USER MARKER
                    mMap.addMarker(
                        MarkerOptions()
                            .position(userLocation)
                            .title("Your Location")
                            .icon(
                                BitmapDescriptorFactory
                                    .defaultMarker(
                                        BitmapDescriptorFactory.HUE_AZURE
                                    )
                            )
                    )

                    // JAN AUSHADHI STORES
                    val stores = listOf(

                        Store(
                            "Jan Aushadhi MG Road",
                            LatLng(12.9716, 77.5946),
                            true
                        ),

                        Store(
                            "Jan Aushadhi Indiranagar",
                            LatLng(12.9611, 77.6387),
                            true
                        ),

                        Store(
                            "Jan Aushadhi Koramangala",
                            LatLng(12.9279, 77.6271),
                            true
                        ),

                        Store(
                            "Jan Aushadhi Jayanagar",
                            LatLng(12.9250, 77.5938),
                            false
                        )
                    )

                    // NEARBY STORES
                    val nearbyStores =
                        mutableListOf<Pair<Store, Float>>()

                    // DISTANCE CALCULATION
                    for (store in stores) {

                        val results =
                            FloatArray(1)

                        Location.distanceBetween(
                            userLocation.latitude,
                            userLocation.longitude,
                            store.location.latitude,
                            store.location.longitude,
                            results
                        )

                        val distance =
                            results[0]

                        // ONLY STORES WITHIN 10 KM
                        if (distance <= 10000) {

                            nearbyStores.add(
                                Pair(store, distance)
                            )
                        }
                    }

                    // SORT NEAREST FIRST
                    nearbyStores.sortBy { it.second }

                    // DISPLAY STORES
                    for ((index, pair)
                    in nearbyStores.withIndex()) {

                        val store =
                            pair.first

                        val distance =
                            pair.second

                        val km =
                            distance / 1000

                        val status =
                            if (store.isOpen)
                                "Open Now"
                            else
                                "Closed"

                        // ESTIMATED TIME
                        val carTime =
                            (km / 40 * 60).toInt()

                        val bikeTime =
                            (km / 30 * 60).toInt()

                        val walkTime =
                            (km / 5 * 60).toInt()

                        // NEAREST STORE COLOR
                        val markerColor =
                            if (index == 0)
                                BitmapDescriptorFactory.HUE_GREEN
                            else
                                BitmapDescriptorFactory.HUE_RED

                        // STORE MARKER
                        mMap.addMarker(
                            MarkerOptions()
                                .position(store.location)
                                .title(store.name)
                                .snippet(
                                    "Distance: %.2f km\n".format(km) +

                                            "Status: $status\n\n" +

                                            "🚗 Car: $carTime mins\n" +

                                            "🏍 Bike: $bikeTime mins\n" +

                                            "🚶 Walk: $walkTime mins\n\n" +

                                            "Tap for Navigation"
                                )
                                .icon(
                                    BitmapDescriptorFactory
                                        .defaultMarker(
                                            markerColor
                                        )
                                )
                        )
                    }

                    // SHOW NEAREST STORE
                    if (nearbyStores.isNotEmpty()) {

                        val nearestStore =
                            nearbyStores[0].first

                        Toast.makeText(
                            this@MapActivity,
                            "Nearest Store:\n${nearestStore.name}",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    // NAVIGATION ON MARKER CLICK
                    mMap.setOnInfoWindowClickListener {

                        val lat =
                            it.position.latitude

                        val lng =
                            it.position.longitude

                        val navigationIntent =
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(
                                    "google.navigation:q=$lat,$lng&mode=d"
                                )
                            )

                        navigationIntent.setPackage(
                            "com.google.android.apps.maps"
                        )

                        startActivity(
                            navigationIntent
                        )
                    }
                }
            },

            mainLooper
        )
    }
}
