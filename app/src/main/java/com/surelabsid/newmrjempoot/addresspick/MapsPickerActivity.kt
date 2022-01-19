package com.surelabsid.newmrjempoot.addresspick

import android.app.Activity
import android.content.Intent
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.surelabsid.newmrjempoot.R
import com.surelabsid.newmrjempoot.databinding.ActivitMapsPickerBinding
import com.surelabsid.newmrjempoot.model.SavedAddress
import com.surelabsid.newmrjempoot.utils.GPSTracker

class MapsPickerActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivitMapsPickerBinding
    private lateinit var startLocation: LatLng


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitMapsPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        binding.konfirm.setOnClickListener {
            if (binding.extras.text.toString().isNotEmpty()) {
                val savedAddress = SavedAddress()
                savedAddress.address = binding.address.text.toString()
                savedAddress.extra = binding.extras.text.toString()
                savedAddress.label = ""
                savedAddress.lat = startLocation.latitude.toString()
                savedAddress.lon = startLocation.longitude.toString()

                val finishIntent = Intent()
                finishIntent.putExtra(ADDRESSMODEL, savedAddress)
                setResult(Activity.RESULT_OK, finishIntent)
                finish()
            }
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        this.mMap = p0

        val gpsTracker = GPSTracker(this)
        // Add a marker in Sydney and move the camera
        startLocation = LatLng(gpsTracker.latitude, gpsTracker.longitude)
        mMap.addMarker(
            MarkerOptions().position(startLocation).icon(
                BitmapDescriptorFactory.fromResource(R.drawable.pick_start)
            ).draggable(true)
        )

        animateCamera(startLocation)

        val l = Location(getString(R.string.app_name))
        l.latitude = gpsTracker.latitude
        l.longitude = gpsTracker.longitude
        val address = gpsTracker.getCoder(l)
        binding.address.text = address[0].getAddressLine(0)

        mMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragStart(p0: Marker) {

            }

            override fun onMarkerDrag(p0: Marker) {

            }

            override fun onMarkerDragEnd(p0: Marker) {
                animateCamera(p0.position)
                l.latitude = p0.position.latitude
                l.longitude = p0.position.longitude
                val listAddress = gpsTracker.getCoder(l)
                binding.address.text = listAddress[0].getAddressLine(0)
            }

        })
    }

    private fun animateCamera(startLocation: LatLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(startLocation, 15f))

    }

    companion object {
        const val ADDRESSMODEL = "addressModel"
    }
}