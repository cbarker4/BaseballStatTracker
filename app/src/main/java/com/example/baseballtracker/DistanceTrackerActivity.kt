package com.example.baseballtracker

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlin.math.*



class DistanceTrackerActivity : AppCompatActivity(),LocationListener {
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    private lateinit var tvGpsLocation: TextView
    private  var home : Location? = null
    private var dist :Double? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_distancetracker)
        findViewById<Button>(R.id.sethome).setOnClickListener { sethome() }
        findViewById<Button>(R.id.grabball).setOnClickListener{getBall()}

    }


    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode )
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1f, this)
    }

    override fun onLocationChanged(location: Location) {
        tvGpsLocation = findViewById<TextView>(R.id.distancetraveled)
        if (home==null){
            home = Location(location)
            tvGpsLocation.text = "Home Plate Set"
        }
        else {
            dist = acos(sin(toradans(home!!.latitude))*sin(toradans(location.latitude))+ cos(toradans(home!!.latitude))* cos(toradans(location.latitude))*cos(toradans(location.longitude - home!!.longitude))) * 3960
            dist = dist!! * 5280
            dist =  ceil(dist!! * 100)/100
            tvGpsLocation.text = "Distance:  $dist"
//                "Latitude: " + location.latitude + " , Longitude: " + location.longitude
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun sethome() {
      home = null
        getLocation()
    }
    private fun getBall(){

    }
    private fun toradans(degrees :Double):Double{
        return degrees * PI/180
    }

}
