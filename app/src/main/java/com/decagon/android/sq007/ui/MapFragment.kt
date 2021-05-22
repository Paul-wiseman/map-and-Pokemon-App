package com.decagon.android.sq007.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.decagon.android.sq007.databinding.FragmentMapBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*

class MapFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var map: GoogleMap
    private var marker1: Marker? = null
    private var markerBayo: Marker? = null
    private val LOCATION_PERMISSION_REQUEST = 1
//    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
//    private var dbReference: DatabaseReference = database.getReference("test")
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var databaseRef = FirebaseDatabase.getInstance().reference
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var locListener: ValueEventListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //   FirebaseDatabase.getInstance().reference.child("messages").setValue("Hello")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.onResume()
        binding.mapView.getMapAsync(this)
        // creating an instance of the fused location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }
    /*This method is called when the map is ready to be used. The code declared in this method creates a marker with coordinates
    that is fetched from the database and adds the marker to the map.*/
    override fun onMapReady(mMap: GoogleMap) {
        map = mMap
        marker1 = map.addMarker(MarkerOptions().position(LatLng(0.5, 8.2)))
        markerBayo = map.addMarker(MarkerOptions().position(LatLng(0.5, 8.2)).title("Bayo"))
        getLocationAccess() // with this method we request for the user location
        databaseRef.child("Adebayo").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Could not read from database", Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    Log.d("Adebayo", "$snapshot")
                    // get the exact longitude and latitude from the database "test"
                    val location = snapshot.getValue(LocationLogging::class.java)
                    val locationLat = location?.Latitude
                    val locationLong = location?.Longitude
                    // check if the latitude and longitude is not null

                    if (locationLat != null && locationLong != null) {
                        // create a LatLng object from location
                        val latLng = LatLng(locationLat, locationLong)
                        // create a marker at the read location and display it on the map
                        markerBayo?.position = latLng
                        // specify how the map camera is updated
                        val update = CameraUpdateFactory.newLatLngZoom(latLng, 10.0f)
                        // update the camera with the CameraUpdate object
                        map.moveCamera(update)
                    } else {
                        // if location is null , log an error message
                        Log.e("FireBase", "user location cannot be found")
                    }
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }
    /*The code below checks if the app has been granted the ACCESS_FINE_LOCATION permission.
    If it hasn’t, then request it from the user.
    */
    private fun getLocationAccess() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            /*on the access is given we are setting this method to true*/
            map.isMyLocationEnabled = true // enable location
            getLocationUpdates() // get the user location
            // startLocationUpdates()
        } else // request user to allow permission to access location
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST
            )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        /*
        * the request code is either 1 or 0. 1: is when the permission is grated which we are comparing with a
        * constant value(LOCATION_PERMISSION_REQUEST) which has a value of 1*/
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            /*IF the request code is 1 we check if permission to access the location is true */
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            requireContext(),
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
                    requireContext(),
                    "User has not granted location access permission",
                    Toast.LENGTH_LONG
                ).show()
                getActivity()?.getSupportFragmentManager()?.beginTransaction()?.remove(this)?.commit()
            }
        }
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

                    Log.d("MapFrag", "getLocationAccess: ")
                    //  val database = FirebaseDatabase.getInstance()
                    // lateinit var databaseRef: DatabaseReference
                    val locationlogging =
                        LocationLogging(location.latitude, location.longitude)
                    databaseRef.child("userlocation").setValue(locationlogging)
                        .addOnSuccessListener {
                            Toast.makeText(
                                requireContext(),
                                "Locations written into the database",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(
                                requireContext(),
                                "Error occured while writing the locations",
                                Toast.LENGTH_LONG
                            ).show()
                            Log.d("DatabaseLatLong", "onLocationResult: $it")
                        }
                    val latLng = LatLng(location.latitude, location.longitude)
                    val markerOptions = MarkerOptions().position(latLng)
                    marker1?.position = latLng
                    marker1?.title = "Emeka's location"
                    //  map.addMarker(markerOptions)
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
        startLocationUpdates()
    }
    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
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
}
