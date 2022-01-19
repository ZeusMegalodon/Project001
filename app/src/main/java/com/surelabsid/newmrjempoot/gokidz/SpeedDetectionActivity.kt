package com.surelabsid.newmrjempoot.gokidz

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.surelabsid.newmrjempoot.R
import com.surelabsid.newmrjempoot.databinding.ActivitySpeedDetectionBinding
import com.surelabsid.newmrjempoot.utils.GPSTracker

class SpeedDetectionActivity : AppCompatActivity(), LocationListener {
    private lateinit var binding: ActivitySpeedDetectionBinding
    private lateinit var locationManager: LocationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpeedDetectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val gpsEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val networkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        when {
            gpsEnable -> {
                locationReq(LocationManager.GPS_PROVIDER)
            }
            networkEnable -> {
                locationReq(LocationManager.NETWORK_PROVIDER)
            }
            else -> {
                val tracker = GPSTracker(this)
                tracker.showSettingsAlert()
            }
        }
    }

    private fun locationReq(provider: String) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        locationManager.requestLocationUpdates(provider, 1000L, 5F, this)
    }

    override fun onLocationChanged(p0: Location) {
        if (!p0.isFromMockProvider) {
            val speed = p0.speed

            binding.speedometer.setSpeed(speed.toInt(), 1000L)

        }
    }
}