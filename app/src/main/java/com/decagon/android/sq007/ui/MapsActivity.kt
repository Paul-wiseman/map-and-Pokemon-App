package com.decagon.android.sq007.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.decagon.android.sq007.R
import com.decagon.android.sq007.databinding.ActivityMapsBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    // Declare variables
    private lateinit var map: GoogleMap

    private val LOCATION_PERMISSION_REQUEST = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    /** This function checks if permission to access user location is granted or not,
     * if granted then enable user location.
     * */
    private fun getLocationAccess() {
        /*we are checking if permission is given to access the device location
        * */
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            /*on the access is given we are setting this method to true*/
            map.isMyLocationEnabled = true // enable location
            getLocationUpdates()
            // startLocationUpdates()
        } else // request user to allow permission to access location
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST
            )
    }

    // check if permission is granted and enable location else toast a denied message
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        /*
        * the request code is either 1 or 0. 1: is when the permission is grated which we are comparing with a
        * constant value(LOCATION_PERMISSION_REQUEST) which has a value of 1*/
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            /*IF the request code is 1 we check if permission to access the location is true */
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                map.isMyLocationEnabled = true
            } else {
                Toast.makeText(
                    this,
                    "User has not granted location access permission",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        /*with the fused location providers we can access accurate location of the user because it provides method to access the location
        * of the user it also allows you to define the preciseness of the locaiton and find the last location of the device */
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }
    private fun getLocationUpdates() {
        locationRequest = LocationRequest()
        locationRequest.interval = 7500
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY // determine the most accuracy of the devicezZ

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult.locations.isNotEmpty()) {
                    val location = locationResult.lastLocation

                    locationCallback = object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult) {
                            if (locationResult.locations.isNotEmpty()) {
                                val location = locationResult.lastLocation
//                                val database = FirebaseDatabase.getInstance()
                                lateinit var databaseRef: DatabaseReference
                                databaseRef = FirebaseDatabase.getInstance().reference
                                val locationlogging =
                                    LocationLogging(location.latitude, location.longitude)
                                databaseRef.child("userlocation").setValue(locationlogging)
                                    .addOnSuccessListener {
                                        Toast.makeText(
                                            applicationContext,
                                            "Locations written into the database",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(
                                            applicationContext,
                                            "Error occured while writing the locations",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                if (location != null) {
                                    val latLng = LatLng(location.latitude, location.longitude)
                                    val markerOptions = MarkerOptions().position(latLng)
                                    map.addMarker(markerOptions)
                                    map.animateCamera(
                                        CameraUpdateFactory.newLatLngZoom(
                                            latLng,
                                            15f
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                fun startLocationUpdates() {
                    if (ActivityCompat.checkSelfPermission(
                            applicationContext,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                applicationContext,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return
                    }
                    fusedLocationClient.requestLocationUpdates(
                        locationRequest,
                        locationCallback,
                        null
                    )
                }

                /**
                 * Manipulates the map once available.
                 * This callback is triggered when the map is ready to be used.
                 * This is where we can add markers or lines, add listeners or move the camera. In this case,
                 * we just add a marker near Sydney, Australia.
                 * If Google Play services is not installed on the device, the user will be prompted to install
                 * it inside the SupportMapFragment. This method will only be triggered once the user has
                 * installed Google Play services and returned to the app.
                 */
//                override fun onMapReady(googleMap: GoogleMap) {
//                    // Add a marker in Sydney and move the camera
//                    // The latitude of Benin City, Nigeria is 6.339185, and the longitude is 5.617447
//                    /*val sydney = LatLng(6.339185, 5.617447)
//
//        val zoomLevel = 15f
//        mMap.addMarker(MarkerOptions().position(sydney).title("Benin City | Nigeria"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel))*/
//                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        getLocationAccess()
    }
}
